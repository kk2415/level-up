package com.levelup.core.dto.article;

import com.levelup.core.domain.Article.ArticleType;
import lombok.Data;

@Data
public class ArticleRequest {

    private String title;
    private String writer;
    private String content;
    private ArticleType articleType;

}
