package com.levelup.core.dto.comment;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;


@Getter
@NoArgsConstructor
public class CreateCommentRequest {

    private String memberEmail;

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleType identity;

    private CreateCommentRequest(String memberEmail, Long articleId, String content, ArticleType identity) {
        this.memberEmail = memberEmail;
        this.articleId = articleId;
        this.content = content;
        this.identity = identity;
    }

    public static CreateCommentRequest of(String memberEmail, Long articleId, String content, ArticleType identity) {
        return new CreateCommentRequest(memberEmail, articleId, content, identity);
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
