package com.levelup.api.controller.v1.dto.response.article;

import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.common.util.DateFormat;
import com.levelup.article.domain.entity.ArticleType;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ArticleUpdateResponse {

    private Long id;
    private String title;
    private String writer;
    private String content;
    private String createdAt;
    private Long views;
    private ArticleType articleType;

    protected ArticleUpdateResponse() {}

    private ArticleUpdateResponse(Long id,
                                 String title,
                                 String writer,
                                 String content,
                                 String createdAt,
                                 Long views,
                                 ArticleType articleType)
    {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.views = views;
        this.articleType = articleType;
    }

    public static ArticleUpdateResponse from(ArticleDto dto) {
        return new ArticleUpdateResponse(
                dto.getArticleId(),
                dto.getTitle(),
                dto.getWriter(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getContent(),
                dto.getViews(),
                dto.getArticleType()
        );
    }
}
