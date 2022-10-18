package com.levelup.member.domain.service.dto;

import com.levelup.member.domain.entity.EmailAuth;
import com.levelup.member.domain.entity.EmailAuthType;
import com.levelup.member.domain.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailAuthDto {

    private EmailAuthType authType;
    private String securityCode;
    private Boolean isAuthenticated;

    protected EmailAuthDto() {}

    private EmailAuthDto(EmailAuthType authType, String securityCode, Boolean isAuthenticated) {
        this.authType = authType;
        this.securityCode = securityCode;
        this.isAuthenticated = isAuthenticated;
    }

    public static EmailAuthDto of(EmailAuthType authType, String securityCode, Boolean isAuthenticated) {
        return new EmailAuthDto(authType, securityCode, isAuthenticated);
    }

    public static EmailAuthDto from(EmailAuth emailAuth) {
        return new EmailAuthDto(emailAuth.getAuthType(), emailAuth.getSecurityCode(), emailAuth.getIsAuthenticated());
    }

    public EmailAuth toEntity(Member member) {
        return EmailAuth.of(
                authType,
                member.getEmail(),
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                member);
    }
}
