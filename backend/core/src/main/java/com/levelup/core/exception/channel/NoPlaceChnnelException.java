package com.levelup.core.exception.channel;

public class NoPlaceChnnelException extends RuntimeException {

    public NoPlaceChnnelException() {
        super();
    }

    public NoPlaceChnnelException(String message) {
        super(message);
    }

    public NoPlaceChnnelException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPlaceChnnelException(Throwable cause) {
        super(cause);
    }

    protected NoPlaceChnnelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
