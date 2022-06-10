package com.levelup.core.dto.comment;

import com.levelup.core.domain.Article.ArticleCategory;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class CreateReplyCommentRequest {

    @NotNull
    private Long parentId;

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleCategory identity;

    public Comment toEntity(Member member, Object article) {
        Comment comment = Comment.builder()
                .member(member)
                .writer(member.getName())
                .dateCreated(LocalDateTime.now())
                .content(content)
                .voteCount(0L)
                .child(new ArrayList<>())
                .build();
        comment.setArticle(article);

        return comment;
    }

}
