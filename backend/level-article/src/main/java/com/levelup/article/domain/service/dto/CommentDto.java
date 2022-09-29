package com.levelup.article.domain.service.dto;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.entity.ArticleComment;
import com.levelup.member.domain.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class CommentDto {

    protected Long commentId;
    protected Long memberId;
    protected String writer;
    protected String content;
    protected LocalDateTime createdAt;
    protected Long voteCount;
    protected Long replyCount;
    protected Long articleId;
    protected ArticleType articleType;

    public CommentDto(Long commentId,
                      Long memberId,
                      String writer,
                      String content,
                      LocalDateTime createdAt,
                      Long voteCount,
                      Long replyCount,
                      Long articleId,
                      ArticleType articleType) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.replyCount = replyCount;
        this.articleId = articleId;
        this.articleType = articleType;
    }

    public static CommentDto from(ArticleComment comment) {
        return new CommentDto(
            comment.getId(),
            comment.getMember().getId(),
            comment.getMember().getNickname(),
            comment.getContent(),
            comment.getCreatedAt(),
            (long) comment.getCommentVotes().size(),
            (long) comment.getChild().size(),
            comment.getArticle().getId(),
            comment.getArticle().getArticleType()
        );
    }

    public static CommentDto of(Long articleId, String content, ArticleType identity) {
        return new CommentDto(
                null,
                null,
                null,
                content,
                null,
                null,
                null,
                articleId,
                identity);
    }

    public ArticleComment toEntity(Member member, Article article) {
        ArticleComment comment = ArticleComment.builder()
                .member(member)
                .content(content)
                .child(new ArrayList<>())
                .commentVotes(new ArrayList<>())
                .build();

        comment.setArticle(article);
        return comment;
    }
}
