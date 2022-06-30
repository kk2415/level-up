package com.levelup.core.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailAuthResponse {

    private String securityCode;
    private Boolean isConfirmed;

}
