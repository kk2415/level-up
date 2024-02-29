package com.levelup.article.domain.service.dto;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.Comment;
import com.levelup.article.domain.entity.Writer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class ReplyCommentDto extends CommentDto {

    private Long parentId;

    public ReplyCommentDto(Long commentId,
                           Long memberId,
                           Long parentId,
                           String writer,
                           String content,
                           LocalDateTime createdAt,
                           Long voteCount,
                           Long replyCount,
                           Long articleId,
                           ArticleType articleType) {
        super(commentId, memberId, writer, content, createdAt, voteCount, replyCount, articleId, articleType);
        this.parentId = parentId;
    }

    public static ReplyCommentDto from(Comment comment) {
        return new ReplyCommentDto(
            comment.getId(),
            comment.getWriter().getMemberId(),
            comment.getParent().getId(),
            comment.getWriter().getNickname(),
            comment.getContent(),
            comment.getCreatedAt(),
            (long) comment.getCommentVotes().size(),
            (long) comment.getChild().size(),
            comment.getArticle().getId(),
            comment.getArticle().getArticleType()
        );
    }

    public static ReplyCommentDto of(Long articleId, Long parentId, String content, ArticleType articleType) {
        return new ReplyCommentDto(
                null,
                null,
                parentId,
                null,
                content,
                null,
                null,
                null,
                articleId,
                articleType);
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
