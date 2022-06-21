package com.levelup.core.dto.vote;

import com.levelup.core.domain.Article.ArticleType;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CreateVoteRequest {

    @NotNull
    private Long articleId;

    @NotNull
    private ArticleType identity;

}
