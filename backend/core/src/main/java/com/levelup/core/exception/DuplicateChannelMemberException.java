package com.levelup.core.exception;

public class DuplicateChannelMemberException extends RuntimeException {
    public DuplicateChannelMemberException() {
        super();
    }

    public DuplicateChannelMemberException(String message) {
        super(message);
    }

    public DuplicateChannelMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateChannelMemberException(Throwable cause) {
        super(cause);
    }

    protected DuplicateChannelMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
