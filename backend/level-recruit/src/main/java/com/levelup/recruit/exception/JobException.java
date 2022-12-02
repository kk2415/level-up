package com.levelup.recruit.exception;

import com.levelup.common.exception.BusinessException;
import com.levelup.common.exception.ErrorCode;

public class JobException extends BusinessException {

    public JobException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JobException(String message, int httpStatus) {
        super(message, httpStatus);
    }
}
