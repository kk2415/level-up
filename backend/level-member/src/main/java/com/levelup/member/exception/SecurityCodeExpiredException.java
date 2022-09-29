package com.levelup.member.exception;

public class SecurityCodeExpiredException extends RuntimeException {
    public SecurityCodeExpiredException() {
        super();
    }

    public SecurityCodeExpiredException(String message) {
        super(message);
    }

    public SecurityCodeExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityCodeExpiredException(Throwable cause) {
        super(cause);
    }

    protected SecurityCodeExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
