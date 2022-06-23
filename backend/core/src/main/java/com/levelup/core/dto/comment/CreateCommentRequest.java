package com.levelup.core.dto.comment;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class CreateCommentRequest {

    private String memberEmail;

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleType identity;

    public Comment toEntity(Member member, Article article) {
        Comment comment = Comment.builder()
                .member(member)
                .writer(member.getName())
                .content(content)
                .voteCount(0L)
                .replyCount(0L)
                .child(new ArrayList<>())
                .build();
        comment.setArticle(article);

        return comment;
    }

}
