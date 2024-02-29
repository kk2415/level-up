package com.levelup.channel.exception;

import com.levelup.common.exception.ErrorCode;

public class NoPlaceChannelException extends ChannelException {

    public NoPlaceChannelException(ErrorCode errorCode) {
        super(errorCode);
    }
}
