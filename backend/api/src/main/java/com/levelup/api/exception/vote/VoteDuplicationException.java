package com.levelup.api.exception.vote;

public class VoteDuplicationException extends RuntimeException {

    public VoteDuplicationException() {
        super();
    }

    public VoteDuplicationException(String message) {
        super(message);
    }

    public VoteDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoteDuplicationException(Throwable cause) {
        super(cause);
    }

    protected VoteDuplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
