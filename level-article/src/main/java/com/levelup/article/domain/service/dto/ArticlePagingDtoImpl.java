package com.levelup.article.domain.service.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticlePagingDtoImpl implements ArticlePagingDto {

    private Long articleId;
    private String title;
    private Long views;
    private String articleType;
    private LocalDateTime createdAt;
    private String writer;
    private Long commentCount;
    private Long voteCount;

    protected ArticlePagingDtoImpl() {}

    private ArticlePagingDtoImpl(
            Long articleId,
            String title,
            Long views,
            String articleType,
            LocalDateTime createdAt,
            String writer,
            Long commentCount,
            Long voteCount)
    {
        this.articleId = articleId;
        this.title = title;
        this.views = views;
        this.articleType = articleType;
        this.createdAt = createdAt;
        this.writer = writer;
        this.commentCount = commentCount;
        this.voteCount = voteCount;
    }

    public static ArticlePagingDtoImpl of(
            Long articleId,
            String title,
            Long views,
            String articleType,
            LocalDateTime createdAt,
            String writer,
            Long commentCount,
            Long voteCount)
    {
        return new ArticlePagingDtoImpl(articleId, articleType, views, articleType, createdAt, writer, commentCount, voteCount);
    }
}
