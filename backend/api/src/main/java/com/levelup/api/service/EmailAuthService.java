package com.levelup.api.service;

import com.levelup.api.dto.emailAuth.EmailAuthRequest;
import com.levelup.api.util.email.EmailSender;
import com.levelup.api.util.email.EmailStuff;
import com.levelup.api.util.email.EmailSubject;
import com.levelup.api.util.email.EmailTemplateName;
import com.levelup.core.domain.emailAuth.EmailAuth;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.emailAuth.EmailCodeExpiredException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.emailAuth.NotMatchSecurityCodeException;
import com.levelup.core.repository.emailAuth.EmailAuthRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailSender emailSender;
    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;

    public void save(EmailAuthRequest request, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 계정입니다."));

        EmailAuth emailAuth = request.toEntity(member);
        emailSender.sendEmail(
                EmailStuff.of(
                        member.getEmail(),
                        EmailSubject.AUTHENTICATE_MAIL,
                        emailAuth.getSecurityCode(),
                        EmailTemplateName.AUTHENTICATE_MAIL)
        );

        emailAuthRepository.save(emailAuth);
    }

    public void authenticateEmail(EmailAuthRequest request, String email) {
        EmailAuth emailAuth = emailAuthRepository.findByEmailAndAuthType(email, request.getAuthType().name());

        validateSecurityCode(request.getSecurityCode(), emailAuth);
        emailAuth.setIsAuthenticated(true);

        Member member = emailAuth.getMember();
        member.addRole(Role.of(RoleName.MEMBER, member)); //인증 후 권한을 회원으로 승급
    }

    private void validateSecurityCode(String securityCode, EmailAuth emailAuth) {
        if (isExpired(emailAuth.getExpireDate())) {
            throw new EmailCodeExpiredException("인증코드가 만료되었습니다.");
        }

        if (!emailAuth.getSecurityCode().equals(securityCode)) {
            throw new NotMatchSecurityCodeException("인증번호가 일치하지 않습니다.");
        }
    }

    private boolean isExpired(LocalDateTime expireDate) {
        return LocalDateTime.now().isAfter(expireDate);
    }
}
