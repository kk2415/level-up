package com.levelup.member.exception;

public class ProfileImageNotFoundException extends RuntimeException {

    public ProfileImageNotFoundException() {
        super();
    }

    public ProfileImageNotFoundException(String message) {
        super(message);
    }

    public ProfileImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileImageNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProfileImageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
