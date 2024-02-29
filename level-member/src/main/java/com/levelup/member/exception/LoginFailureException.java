package com.levelup.member.exception;

import com.levelup.common.exception.ErrorCode;

public class LoginFailureException extends MemberException {

    public LoginFailureException(ErrorCode errorCode) {
        super(errorCode);
    }
}
