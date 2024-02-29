package com.levelup.member.exception;

import com.levelup.common.exception.ErrorCode;

public class SecurityCodeExpiredException extends MemberException {

    public SecurityCodeExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
