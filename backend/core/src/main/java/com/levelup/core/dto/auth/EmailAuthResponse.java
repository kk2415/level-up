package com.levelup.core.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EmailAuthResponse {

    private String securityCode;
    private Boolean isConfirmed;

    private EmailAuthResponse(String securityCode, Boolean isConfirmed) {
        this.securityCode = securityCode;
        this.isConfirmed = isConfirmed;
    }

    public static EmailAuthResponse of(String securityCode, Boolean isConfirmed) {
        return new EmailAuthResponse(securityCode, isConfirmed);
    }
}
