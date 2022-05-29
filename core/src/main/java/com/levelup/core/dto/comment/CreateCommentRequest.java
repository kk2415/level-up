package com.levelup.core.dto.comment;

import com.levelup.core.domain.comment.ArticleIdentity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequest {

    private String memberEmail;

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleIdentity identity;

}
