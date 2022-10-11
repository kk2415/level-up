package com.levelup.api.controller.v1.dto.request.article;

import com.levelup.article.domain.service.dto.ReplyCommentDto;
import com.levelup.article.domain.entity.ArticleType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ReplyCommentRequest {

    @NotNull
    private Long parentId;

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleType identity;

    protected ReplyCommentRequest() {}

    private ReplyCommentRequest(Long parentId, Long articleId, String content, ArticleType identity) {
        this.parentId = parentId;
        this.articleId = articleId;
        this.content = content;
        this.identity = identity;
    }

    public ReplyCommentDto toDto() {
        return ReplyCommentDto.of(articleId, parentId, content, identity);
    }

    public static ReplyCommentRequest of(Long parentId, Long articleId, String content, ArticleType identity) {
        return new ReplyCommentRequest(parentId, articleId, content, identity);
    }
}
