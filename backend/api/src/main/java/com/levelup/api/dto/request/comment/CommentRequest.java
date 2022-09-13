package com.levelup.api.dto.request.comment;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.service.comment.CommentDto;
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
