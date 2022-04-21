package com.together.levelup.exception;

public class NotFoundImageException extends RuntimeException {

    public NotFoundImageException() {
        super();
    }

    public NotFoundImageException(String message) {
        super(message);
    }

    public NotFoundImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundImageException(Throwable cause) {
        super(cause);
    }

    protected NotFoundImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
