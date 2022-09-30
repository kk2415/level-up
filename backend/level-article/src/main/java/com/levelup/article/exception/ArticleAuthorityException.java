package com.levelup.article.exception;

import com.levelup.common.exception.ErrorCode;

public class ArticleAuthorityException extends ArticleException {

    public ArticleAuthorityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
