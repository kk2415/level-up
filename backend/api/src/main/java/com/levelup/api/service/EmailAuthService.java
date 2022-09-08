package com.levelup.api.service;

import com.levelup.api.util.EmailSender;
import com.levelup.core.domain.emailAuth.EmailAuth;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.auth.EmailAuthResponse;
import com.levelup.core.exception.emailAuth.EmailCodeExpiredException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.emailAuth.NotMatchSecurityCodeException;
import com.levelup.core.repository.emailAuth.EmailAuthRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAuthService {

    private final Long EXPIRATION_SECOND = 500L;

    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final EmailSender emailService;

    public void sendSecurityCode(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

        emailAuthRepository.findByMemberId(memberId).ifPresentOrElse((authEmail) -> { //재발송
            String securityCode = EmailAuth.createSecurityCode();
            authEmail.setSecurityCode(securityCode);
            authEmail.setReceivedDate(LocalDateTime.now());

            emailService.sendConfirmEmail(member.getEmail(), authEmail.getSecurityCode());
        }, () -> {
            EmailAuth authEmail = EmailAuth.from(member); //처음 발송

            emailAuthRepository.save(authEmail);
            emailService.sendConfirmEmail(member.getEmail(), authEmail.getSecurityCode());
        });
    }

    @CacheEvict(cacheNames = "member", allEntries = true)
    public EmailAuthResponse confirmEmail(String securityCode, Long memberId) {
        EmailAuth emailAuth = emailAuthRepository.findByMemberId(memberId)
                .orElseThrow(() -> {throw new MemberNotFoundException("해당하는 회원을 찾을 수 없습니다");});

        validateSecurityCode(securityCode, emailAuth);
        emailAuth.setConfirmed(true);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
        member.addRole(Role.of(RoleName.MEMBER, member)); //인증 후 권한을 회원으로 승급

        return EmailAuthResponse.of(securityCode, true);
    }

    private void validateSecurityCode(String securityCode, EmailAuth emailAuth) {
        if (isExpired(emailAuth.getReceivedDate())) {
            throw new EmailCodeExpiredException("인증코드가 만료되었습니다.");
        }

        if (!emailAuth.getSecurityCode().equals(securityCode)) {
            throw new NotMatchSecurityCodeException("인증번호가 일치하지 않습니다.");
        }
    }

    private boolean isExpired(LocalDateTime receivedDate) {
        long between = Duration.between(receivedDate, LocalDateTime.now()).getSeconds();

        return between > EXPIRATION_SECOND;
    }
}
