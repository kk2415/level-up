package com.levelup.article.exception;

public class ArticleAuthorityException extends RuntimeException {

    public ArticleAuthorityException() {
        super();
    }

    public ArticleAuthorityException(String message) {
        super(message);
    }

    public ArticleAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleAuthorityException(Throwable cause) {
        super(cause);
    }

    protected ArticleAuthorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
