package com.levelup.core.exception;

public class NotMatchSecurityCodeException extends RuntimeException {

    public NotMatchSecurityCodeException() {
        super();
    }

    public NotMatchSecurityCodeException(String message) {
        super(message);
    }

    public NotMatchSecurityCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMatchSecurityCodeException(Throwable cause) {
        super(cause);
    }

    protected NotMatchSecurityCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
