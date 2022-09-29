package com.levelup.member.exception;

import lombok.Getter;

@Getter
public enum JwtException {

    SUCCESS(true), EXPIRED(false), SIGNATURE(false);

    private final boolean valid;

    JwtException(boolean validation) {
        this.valid = validation;
    }
}
