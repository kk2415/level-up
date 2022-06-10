package com.levelup.core.dto.article;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArticleResponse {

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.writer = article.getWriter();
        this.content = article.getContent();
        this.voteCount = article.getVoteCount();
        this.views = article.getViews();
        this.category = article.getCategory();
    }

    private Long id;
    private String title;
    private String writer;
    private String content;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private ArticleCategory category;

}
