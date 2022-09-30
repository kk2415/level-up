package com.levelup.member.exception;

import com.levelup.common.exception.BusinessException;
import com.levelup.common.exception.ErrorCode;

public class MemberException extends BusinessException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
