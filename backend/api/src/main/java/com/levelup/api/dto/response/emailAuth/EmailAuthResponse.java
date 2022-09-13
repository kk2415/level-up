package com.levelup.api.dto.response.emailAuth;

import lombok.Getter;

@Getter
public class EmailAuthResponse {

    private String securityCode;
    private Boolean isConfirmed;

    protected EmailAuthResponse() {}

    private EmailAuthResponse(String securityCode, Boolean isConfirmed) {
        this.securityCode = securityCode;
        this.isConfirmed = isConfirmed;
    }

    public static EmailAuthResponse of(String securityCode, Boolean isConfirmed) {
        return new EmailAuthResponse(securityCode, isConfirmed);
    }
}
