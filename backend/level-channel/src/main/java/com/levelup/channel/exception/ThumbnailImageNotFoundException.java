package com.levelup.channel.exception;

public class ThumbnailImageNotFoundException extends RuntimeException {

    public ThumbnailImageNotFoundException() {
        super();
    }

    public ThumbnailImageNotFoundException(String message) {
        super(message);
    }

    public ThumbnailImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThumbnailImageNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ThumbnailImageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
