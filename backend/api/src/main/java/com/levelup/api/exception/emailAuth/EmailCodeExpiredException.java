package com.levelup.api.exception.emailAuth;

public class EmailCodeExpiredException extends RuntimeException {
    public EmailCodeExpiredException() {
        super();
    }

    public EmailCodeExpiredException(String message) {
        super(message);
    }

    public EmailCodeExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailCodeExpiredException(Throwable cause) {
        super(cause);
    }

    protected EmailCodeExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
