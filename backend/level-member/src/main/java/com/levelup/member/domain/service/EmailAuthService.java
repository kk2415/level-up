package com.levelup.member.domain.service;

import com.levelup.common.util.email.EmailSender;
import com.levelup.common.util.email.EmailStuff;
import com.levelup.common.util.email.EmailSubject;
import com.levelup.common.util.email.EmailTemplateName;
import com.levelup.member.domain.entity.EmailAuth;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.exception.MemberNotFoundException;
import com.levelup.member.exception.NotMatchSecurityCodeException;
import com.levelup.member.exception.SecurityCodeExpiredException;
import com.levelup.member.domain.repository.EmailAuthRepository;
import com.levelup.member.domain.repository.MemberRepository;
import com.levelup.member.domain.service.dto.EmailAuthDto;
import lombok.RequiredArgsConstructor;
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

    public void save(EmailAuthDto dto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 계정입니다."));

        EmailAuth emailAuth = dto.toEntity(member);
        sendEmail(member.getEmail(), emailAuth.getSecurityCode());

        emailAuthRepository.save(emailAuth);
    }

    private void sendEmail(String email, String securityCode) {
        EmailStuff emailStuff
                = EmailStuff.of(email, EmailSubject.AUTHENTICATE_MAIL, securityCode, EmailTemplateName.AUTHENTICATE_MAIL);

        emailSender.sendEmail(emailStuff);
    }



    public void authenticateEmail(EmailAuthDto dto, String email) {
        EmailAuth emailAuth = emailAuthRepository.findByEmailAndAuthType(email, dto.getAuthType().name())
                        .orElseThrow();

        validateSecurityCode(dto.getSecurityCode(), emailAuth);
        emailAuth.setIsAuthenticated(true);

        Member member = emailAuth.getMember();
        member.addRole(Role.of(RoleName.MEMBER, member)); //인증 후 권한을 회원으로 승급
    }

    private void validateSecurityCode(String securityCode, EmailAuth emailAuth) {
        if (isExpired(emailAuth.getExpireDate())) {
            throw new SecurityCodeExpiredException("인증코드가 만료되었습니다.");
        }

        if (!emailAuth.getSecurityCode().equals(securityCode)) {
            throw new NotMatchSecurityCodeException("인증번호가 일치하지 않습니다.");
        }
    }

    private boolean isExpired(LocalDateTime expireDate) {
        return LocalDateTime.now().isAfter(expireDate);
    }
}
