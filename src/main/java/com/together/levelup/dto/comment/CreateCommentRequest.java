package com.together.levelup.dto.comment;

import com.together.levelup.domain.ArticleIdentity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequest {

    @NotNull
    private String memberEmail;

    @NotNull
    private Long articleId;

    @NotNull
    private String content;

    @NotNull
    private ArticleIdentity identity;

}
