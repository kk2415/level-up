package com.levelup.api.dto.article;

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
}
