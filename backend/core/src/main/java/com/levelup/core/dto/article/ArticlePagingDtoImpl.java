package com.levelup.core.dto.article;

import lombok.Getter;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
public class ArticlePagingDtoImpl {

    private BigInteger articleId;
    private String title;
    private BigInteger views;
    private String articleType;
    private Timestamp createdAt;
    private String writer;
    private BigInteger commentCount;
    private BigInteger voteCount;

    protected ArticlePagingDtoImpl() {}

    public ArticlePagingDtoImpl(BigInteger articleId,
                                String title,
                                BigInteger views,
                                String articleType,
                                Timestamp createdAt,
                                String writer,
                                BigInteger commentCount,
                                BigInteger voteCount) {
        this.articleId = articleId;
        this.title = title;
        this.views = views;
        this.articleType = articleType;
        this.createdAt = createdAt;
        this.writer = writer;
        this.commentCount = commentCount;
        this.voteCount = voteCount;
    }
}
