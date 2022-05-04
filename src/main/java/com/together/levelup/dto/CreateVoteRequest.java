package com.together.levelup.dto;

import com.together.levelup.domain.comment.ArticleIdentity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateVoteRequest {

    @NotNull
    private Long articleId;

    @NotNull
    private ArticleIdentity identity;

}
