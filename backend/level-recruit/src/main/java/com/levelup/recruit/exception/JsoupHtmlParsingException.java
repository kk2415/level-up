package com.levelup.recruit.exception;

import com.levelup.common.exception.ErrorCode;

public class JsoupHtmlParsingException extends JobException {

    public JsoupHtmlParsingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
