package com.levelup.api.controller.v1.dto.request.article;

import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.Comment;
import com.levelup.member.domain.entity.Member;
import com.levelup.article.domain.service.dto.CommentDto;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;


@Getter
public class CommentRequest {

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleType identity;

    protected CommentRequest() {}

    private CommentRequest(Long articleId, String content, ArticleType identity) {
        this.articleId = articleId;
        this.content = content;
        this.identity = identity;
    }

    public static CommentRequest of(Long articleId, String content, ArticleType identity) {
        return new CommentRequest(articleId, content, identity);
    }

    public CommentDto toDto() {
        return CommentDto.of(articleId, content, identity);
    }
}
