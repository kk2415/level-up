package com.levelup.api.dto.request.emailAuth;

import com.levelup.api.dto.service.emailAuth.EmailAuthDto;
import com.levelup.core.domain.emailAuth.EmailAuth;
import com.levelup.core.domain.emailAuth.EmailAuthType;
import com.levelup.core.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailAuthRequest {

    private String securityCode;
    private EmailAuthType authType;

    protected EmailAuthRequest() {}

    private EmailAuthRequest(String securityCode, EmailAuthType authType) {
        this.securityCode = securityCode;
        this.authType = authType;
    }

    public static EmailAuthRequest of(String securityCode, EmailAuthType authType) {
        return new EmailAuthRequest(securityCode, authType);
    }

    public EmailAuthDto toDto() {
        return EmailAuthDto.of(authType, securityCode, false);
    }

    public EmailAuth toEntity(Member member) {
        return EmailAuth.builder()
                .member(member)
                .authType(authType)
                .email(member.getEmail())
                .securityCode(EmailAuth.createSecurityCode())
                .isAuthenticated(false)
                .receivedDate(LocalDateTime.now())
                .expireDate(LocalDateTime.now().plusMinutes(1))
                .build();
    }
}
