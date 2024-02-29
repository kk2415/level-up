package com.levelup.article.exception;

import com.levelup.common.exception.BusinessException;
import com.levelup.common.exception.ErrorCode;

public class ArticleException extends BusinessException {

    public ArticleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
