package com.levelup.recruit.exception;

import com.levelup.common.exception.ErrorCode;

public class JsoupConnectionException extends JobException {

    public JsoupConnectionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JsoupConnectionException(String url, ErrorCode errorCode) {
        super(errorCode.getMessage() + " - " + url, errorCode.getHttpStatus());
    }
}
