package com.levelup.image.exception;

import com.levelup.common.exception.BusinessException;
import com.levelup.common.exception.ErrorCode;

public class FileException extends BusinessException {

    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
