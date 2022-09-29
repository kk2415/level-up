package com.levelup.channel.exception;

public class ChannelMemberDuplicationException extends RuntimeException {
    public ChannelMemberDuplicationException() {
        super();
    }

    public ChannelMemberDuplicationException(String message) {
        super(message);
    }

    public ChannelMemberDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelMemberDuplicationException(Throwable cause) {
        super(cause);
    }

    protected ChannelMemberDuplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
