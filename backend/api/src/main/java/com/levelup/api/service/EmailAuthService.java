package com.levelup.api.service;

import com.levelup.core.domain.auth.EmailAuth;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.auth.EmailAuthResponse;
import com.levelup.core.exception.EmailCodeExpiredException;
import com.levelup.core.exception.MemberNotFoundException;
import com.levelup.core.exception.NotMatchSecurityCodeException;
import com.levelup.core.repository.auth.EmailAuthRepository;
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
    private final EmailService emailService;

    /**
     * 인증코드 발송
     * */
    public void sendSecurityCode(Long memberId) {
        Member member = memberRepository.findById(memberId);

        emailAuthRepository.findByMemberId(memberId).ifPresentOrElse((authEmail) -> { //재발송
            String securityCode = EmailAuth.createSecurityCode();
            authEmail.setSecurityCode(securityCode);
            authEmail.setReceivedDate(LocalDateTime.now());

            emailService.sendConfirmEmail(member.getEmail(), authEmail.getSecurityCode());
        }, () -> {
            EmailAuth authEmail = EmailAuth.createAuthEmail(member.getEmail()); //처음 발송
            member.setEmailAuth(authEmail);

            emailService.sendConfirmEmail(member.getEmail(), authEmail.getSecurityCode());
        });
    }


    /**
     * 이메일 인증
     * */
    @CacheEvict(cacheNames = "member", allEntries = true)
    public EmailAuthResponse confirmEmail(String securityCode, Long memberId) {
        EmailAuth auth = emailAuthRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    throw new MemberNotFoundException("해당하는 회원을 찾을 수 없습니다");
                });

        if (!auth.getSecurityCode().equals(securityCode)) {
            throw new NotMatchSecurityCodeException("인증번호가 일치하지 않습니다.");
        }

        isExpired(auth.getReceivedDate());

        auth.setConfirmed(true);
        Member member = memberRepository.findById(memberId);
        member.setAuthority(Authority.MEMBER); //인증 후 권한을 회원으로 승급

        return new EmailAuthResponse(securityCode, true);
    }


    /**
     * 인증코드 만료 시간 체크
     * */
    public void isExpired(LocalDateTime receivedDate) {
        long between = Duration.between(receivedDate, LocalDateTime.now()).getSeconds();

        if (between > EXPIRATION_SECOND) {
            throw new EmailCodeExpiredException("인증코드가 만료되었습니다.");
        }
    }

}
