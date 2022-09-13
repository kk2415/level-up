package com.levelup.core.exception.channel;

public class NoPlaceChannelException extends RuntimeException {

    public NoPlaceChannelException() {
        super();
    }

    public NoPlaceChannelException(String message) {
        super(message);
    }

    public NoPlaceChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPlaceChannelException(Throwable cause) {
        super(cause);
    }

    protected NoPlaceChannelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
