package com.levelup.api.exception.member;

public class EmailDuplicationException extends RuntimeException {
    public EmailDuplicationException() {
        super();
    }

    public EmailDuplicationException(String message) {
        super(message);
    }

    public EmailDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDuplicationException(Throwable cause) {
        super(cause);
    }

    protected EmailDuplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
