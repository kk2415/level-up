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
                           ArticleType identity) {
        super(commentId, memberId, writer, content, createdAt, voteCount, replyCount, articleId, identity);
        this.parentId = parentId;
    }

    public static ReplyCommentDto from(Comment comment) {
        return new ReplyCommentDto(
            comment.getId(),
            comment.getMember().getId(),
            comment.getParent().getId(),
            comment.getMember().getNickname(),
            comment.getContent(),
            comment.getCreatedAt(),
            (long) comment.getCommentVotes().size(),
            (long) comment.getChild().size(),
            comment.getArticle().getId(),
            comment.getArticle().getArticleType()
        );
    }

    public static ReplyCommentDto of(Long articleId, Long parentId, String content, ArticleType identity) {
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
