package com.levelup.core.dto.article;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ArticleResponse {

    public ArticleResponse(Article article) {
        this.id = article.getArticleId();
        this.memberId = article.getMember().getId();
        this.title = article.getTitle();
        this.writer = article.getWriter();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(article.getCreateAt());
        this.content = article.getContent();
        this.voteCount = article.getVoteCount();
        this.views = article.getViews();
        this.commentCount = article.getCommentCount();
        this.articleType = article.getArticleType();
    }

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

}
