package com.levelup.common.exception;

public class EntityDuplicationException extends BusinessException {

    public EntityDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
