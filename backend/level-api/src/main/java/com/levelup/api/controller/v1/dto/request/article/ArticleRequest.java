package com.levelup.api.controller.v1.dto.request.article;

import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.article.domain.ArticleType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ArticleRequest {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String content;

    @NotNull
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
