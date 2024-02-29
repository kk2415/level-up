package com.levelup.common.exception;

import lombok.Getter;

@Getter
public enum AuthenticationErrorCode {

    SUCCESS(200, "정상적인 토큰입니다.", true),
    FAIL_AUTHENTICATION(401, "사용자 인증 실패", false),

    NULL_BEARER_HEADER(400, "BEARER 헤더가 비어있습니다.", false),
    NULL_TOKEN(400, "토큰이 비어있습니다.", false),
    INVALID_TOKEN(400, "유효하지 않은 토큰입니다.", false),
    EXPIRED_TOKEN(400, "만료된 토큰입니다.", false),
    INVALID_SIGNATURE(400, "유효하지 않은 시그니처입니다.", false);

    private final int httpStatus;
    private final boolean valid;
    private final String message;

    AuthenticationErrorCode(int httpStatus, String message, boolean validation) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.valid = validation;
    }
}
