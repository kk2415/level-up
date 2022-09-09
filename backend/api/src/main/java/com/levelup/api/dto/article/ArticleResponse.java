package com.levelup.api.dto.article;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ArticleResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private ArticleType articleType;

    protected ArticleResponse() {}

    private ArticleResponse(Article article) {
        this.id = article.getId();
        this.memberId = article.getMember().getId();
        this.title = article.getTitle();
        this.writer = article.getMember().getNickname();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(article.getCreatedAt());
        this.content = article.getContent();
        this.voteCount = (long) article.getArticleVotes().size();
        this.views = article.getViews();
        this.commentCount = (long) article.getComments().size();
        this.articleType = article.getArticleType();
    }

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(article);
    }
}
