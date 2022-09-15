package com.levelup.api.controller.v1.dto.request.comment;

import com.levelup.api.service.dto.comment.ReplyCommentDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

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

    public Comment toEntity(Member member, Article article) {
        Comment comment = Comment.builder()
                .member(member)
                .content(content)
                .child(new ArrayList<>())
                .commentVotes(new ArrayList<>())
                .build();

        comment.setArticle(article);
        return comment;
    }
}
