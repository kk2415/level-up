package com.levelup.member.exception;

import com.levelup.common.exception.ErrorCode;

public class NotMatchSecurityCodeException extends MemberException {

    public NotMatchSecurityCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
