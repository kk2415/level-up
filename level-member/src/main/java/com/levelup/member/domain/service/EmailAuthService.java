package com.levelup.member.domain.service;

import com.levelup.common.email.service.EmailService;
import com.levelup.common.email.template.MemberAuthenticationEmailTemplate;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.common.email.EmailSubject;
import com.levelup.member.domain.entity.EmailAuthEntity;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.constant.RoleName;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.member.exception.NotMatchSecurityCodeException;
import com.levelup.member.exception.SecurityCodeExpiredException;
import com.levelup.member.domain.repository.EmailAuthRepository;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.domain.domain.EmailAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailService emailService;
    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;

    public void save(EmailAuth dto, String email) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        EmailAuthEntity emailAuth = dto.toEntity(member);
        sendEmail(member.getEmail(), emailAuth.getSecurityCode());

        emailAuthRepository.save(emailAuth);
    }

    private void sendEmail(String email, String securityCode) {
        emailService.send(email, EmailSubject.AUTHENTICATE_MAIL, MemberAuthenticationEmailTemplate.from(securityCode));
    }


    public void authenticateEmail(EmailAuth dto, String email) {
        EmailAuthEntity emailAuth = emailAuthRepository.findByEmailAndAuthType(email, dto.getAuthType().name())
                        .orElseThrow();

        validateSecurityCode(dto.getSecurityCode(), emailAuth);
        emailAuth.setIsAuthenticated(true);

        MemberEntity member = emailAuth.getMember();
        member.addRole(Role.of(RoleName.MEMBER, member)); //인증 후 권한을 회원으로 승급
    }

    private void validateSecurityCode(String securityCode, EmailAuthEntity emailAuth) {
        if (isExpired(emailAuth.getExpireDate())) {
            throw new SecurityCodeExpiredException(ErrorCode.SECURITY_CODE_EXPIRED);
        }

        if (!emailAuth.getSecurityCode().equals(securityCode)) {
            throw new NotMatchSecurityCodeException(ErrorCode.NOT_MATCH_SECURITY_CODE);
        }
    }

    private boolean isExpired(LocalDateTime expireDate) {
        return LocalDateTime.now().isAfter(expireDate);
    }
}
