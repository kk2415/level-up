package com.together.community.domain.exception;

public class NoPlaceChnnel extends RuntimeException {

    public NoPlaceChnnel() {
        super();
    }

    public NoPlaceChnnel(String message) {
        super(message);
    }

    public NoPlaceChnnel(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPlaceChnnel(Throwable cause) {
        super(cause);
    }

    protected NoPlaceChnnel(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
