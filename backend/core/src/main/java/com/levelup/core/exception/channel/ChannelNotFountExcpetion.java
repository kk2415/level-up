package com.levelup.core.exception.channel;

public class ChannelNotFountExcpetion extends RuntimeException {

    public ChannelNotFountExcpetion() {
        super();
    }

    public ChannelNotFountExcpetion(String message) {
        super(message);
    }

    public ChannelNotFountExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelNotFountExcpetion(Throwable cause) {
        super(cause);
    }

    protected ChannelNotFountExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
