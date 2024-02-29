package com.levelup.channel.exception;

import com.levelup.common.exception.BusinessException;
import com.levelup.common.exception.ErrorCode;

public class ChannelException extends BusinessException {

    public ChannelException(ErrorCode errorCode) {
        super(errorCode);
    }
}
