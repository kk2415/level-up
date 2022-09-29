package com.levelup.article.domain.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface ArticlePagingDto extends Serializable {

    Long getArticleId();
    String getTitle();
    Long getViews();
    String getArticleType();
    LocalDateTime getCreatedAt();
    String getWriter();
    Long getCommentCount();
    Long getVoteCount();
}
