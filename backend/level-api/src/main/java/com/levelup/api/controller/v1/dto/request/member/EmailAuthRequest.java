package com.levelup.api.controller.v1.dto.request.member;

import com.levelup.member.domain.domain.EmailAuth;
import com.levelup.member.domain.constant.EmailAuthType;
import lombok.Getter;

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

    public EmailAuth toDto() {
        return EmailAuth.of(authType, securityCode, false);
    }
}
