package com.levelup.core.dto.comment;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class CreateReplyCommentRequest {

    @NotNull
    private Long parentId;

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleType identity;

    private CreateReplyCommentRequest(Long parentId, Long articleId, String content, ArticleType identity) {
        this.parentId = parentId;
        this.articleId = articleId;
        this.content = content;
        this.identity = identity;
    }

    public static CreateReplyCommentRequest of(Long parentId, Long articleId, String content, ArticleType identity) {
        return new CreateReplyCommentRequest(parentId, articleId, content, identity);
    }

    public Comment toEntity(Member member, Article article) {
        Comment comment = Comment.builder()
                .member(member)
                .writer(member.getNickname())
                .content(content)
                .voteCount(0L)
                .replyCount(0L)
                .child(new ArrayList<>())
                .build();
        comment.setArticle(article);

        return comment;
    }
}
