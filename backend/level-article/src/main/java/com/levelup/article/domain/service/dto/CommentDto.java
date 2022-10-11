package com.levelup.article.domain.service.dto;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.Comment;
import com.levelup.article.domain.entity.Writer;
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

    public static CommentDto from(Comment comment) {
        return new CommentDto(
            comment.getId(),
            comment.getWriter().getMemberId(),
            comment.getWriter().getNickname(),
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

    public Comment toEntity(Writer writer, Article article) {
        Comment comment = Comment.builder()
                .writer(writer)
                .content(content)
                .child(new ArrayList<>())
                .commentVotes(new ArrayList<>())
                .build();

        comment.setArticle(article);
        return comment;
    }
}
