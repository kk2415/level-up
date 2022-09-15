package com.levelup.api.controller.v1.dto.request.article;

import com.levelup.api.service.dto.article.ArticleDto;
import com.levelup.core.domain.article.ArticleType;
import lombok.Getter;

@Getter
public class ArticleRequest {

    private String title;
    private String content;
    private ArticleType articleType;

    protected ArticleRequest() {}

    private ArticleRequest(String title, String content, ArticleType articleType) {
        this.title = title;
        this.content = content;
        this.articleType = articleType;
    }

    public static ArticleRequest of(String title, String content, ArticleType articleType) {
        return new ArticleRequest(title, content, articleType);
    }

    public ArticleDto toDto() {
        return ArticleDto.builder()
                .articleId(null)
                .memberId(null)
                .title(title)
                .writer(null)
                .content(content)
                .createdAt(null)
                .voteCount(0L)
                .views(0L)
                .commentCount(0L)
                .articleType(articleType)
                .build();
    }
}
