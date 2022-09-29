package com.levelup.channel.exception;

public class ChannelMemberNotFoundException extends RuntimeException {

    public ChannelMemberNotFoundException() {
        super();
    }

    public ChannelMemberNotFoundException(String message) {
        super(message);
    }

    public ChannelMemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelMemberNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ChannelMemberNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
