package com.levelup.core.exception.vote;

public class DuplicateVoteException extends RuntimeException {

    public DuplicateVoteException() {
        super();
    }

    public DuplicateVoteException(String message) {
        super(message);
    }

    public DuplicateVoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateVoteException(Throwable cause) {
        super(cause);
    }

    protected DuplicateVoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
