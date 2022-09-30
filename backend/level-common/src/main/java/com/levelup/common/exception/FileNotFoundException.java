package com.levelup.common.exception;

public class FileNotFoundException extends BusinessException {

    public FileNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
