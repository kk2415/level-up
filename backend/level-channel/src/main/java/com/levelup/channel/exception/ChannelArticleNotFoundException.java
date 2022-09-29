package com.levelup.channel.exception;

public class ChannelArticleNotFoundException extends RuntimeException {

    public ChannelArticleNotFoundException() {
        super();
    }

    public ChannelArticleNotFoundException(String message) {
        super(message);
    }

    public ChannelArticleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelArticleNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ChannelArticleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
