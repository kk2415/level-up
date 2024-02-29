package com.levelup.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    protected String message;
    protected int httpStatus;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public BusinessException(String message, int httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
