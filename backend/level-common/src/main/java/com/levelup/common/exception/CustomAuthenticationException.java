package com.levelup.common.exception;

import lombok.Getter;

@Getter
public class CustomAuthenticationException {

    private final AuthenticationErrorCode errorCode;

    public CustomAuthenticationException(AuthenticationErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
