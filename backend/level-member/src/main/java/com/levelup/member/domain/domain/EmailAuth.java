package com.levelup.member.domain.domain;

import com.levelup.member.domain.entity.EmailAuthEntity;
import com.levelup.member.domain.constant.EmailAuthType;
import com.levelup.member.domain.entity.MemberEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailAuth {

    private EmailAuthType authType;
    private String securityCode;
    private Boolean isAuthenticated;

    protected EmailAuth() {}

    private EmailAuth(EmailAuthType authType, String securityCode, Boolean isAuthenticated) {
        this.authType = authType;
        this.securityCode = securityCode;
        this.isAuthenticated = isAuthenticated;
    }

    public static EmailAuth of(EmailAuthType authType, String securityCode, Boolean isAuthenticated) {
        return new EmailAuth(authType, securityCode, isAuthenticated);
    }

    public static EmailAuth from(EmailAuthEntity emailAuth) {
        return new EmailAuth(emailAuth.getAuthType(), emailAuth.getSecurityCode(), emailAuth.getIsAuthenticated());
    }

    public EmailAuthEntity toEntity(MemberEntity member) {
        return EmailAuthEntity.of(
                authType,
                member.getEmail(),
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                member);
    }
}
