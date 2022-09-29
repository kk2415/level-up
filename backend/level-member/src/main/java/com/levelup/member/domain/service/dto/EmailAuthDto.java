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
