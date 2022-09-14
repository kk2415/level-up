package com.levelup.api.dto.service.comment;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    protected ArticleType identity;

    public CommentDto(Long commentId,
                      Long memberId,
                      String writer,
                      String content,
                      LocalDateTime createdAt,
                      Long voteCount,
                      Long replyCount,
                      Long articleId,
                      ArticleType identity) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.replyCount = replyCount;
        this.articleId = articleId;
        this.identity = identity;
    }

    public static CommentDto from(Comment comment) {
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
