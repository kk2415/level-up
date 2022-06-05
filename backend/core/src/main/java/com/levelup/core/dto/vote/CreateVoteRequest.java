package com.levelup.core.dto.vote;

import com.levelup.core.domain.comment.ArticleIdentity;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CreateVoteRequest {

    @NotNull
    private Long articleId;

    @NotNull
    private ArticleIdentity identity;

}
