package com.levelup.core.exception;


public class NotConfirmedEmailException extends RuntimeException {

    public NotConfirmedEmailException() {
        super();
    }

    public NotConfirmedEmailException(String message) {
        super(message);
    }

    public NotConfirmedEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotConfirmedEmailException(Throwable cause) {
        super(cause);
    }

    protected NotConfirmedEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
