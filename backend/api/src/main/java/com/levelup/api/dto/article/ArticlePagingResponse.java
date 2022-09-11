package com.levelup.api.dto.article;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.article.Article;
import com.levelup.core.dto.article.ArticlePagingDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ArticlePagingResponse {

    private Long id;
    private String title;
    private String writer;
    private Long views;
    private String articleType;
    private String createdAt;
    private Long voteCount;
    private Long commentCount;

    protected ArticlePagingResponse() {}

    private ArticlePagingResponse(Long id,
                                  String title,
                                  String writer,
                                  Long views,
                                  String articleType,
                                  String createdAt,
                                  Long voteCount,
                                  Long commentCount) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.views = views;
        this.articleType = articleType;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.commentCount = commentCount;
    }

    public static ArticlePagingResponse from(ArticlePagingDto articlePagingDto) {
        return new ArticlePagingResponse(
                articlePagingDto.getArticleId(),
                articlePagingDto.getTitle(),
                articlePagingDto.getWriter(),
                articlePagingDto.getViews(),
                articlePagingDto.getArticleType(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(articlePagingDto.getCreatedAt()),
                articlePagingDto.getVoteCount(),
                articlePagingDto.getCommentCount()
        );
    }

    public static ArticlePagingResponse from(Article article) {
        return new ArticlePagingResponse(
                article.getId(),
                article.getTitle(),
                article.getMember().getNickname(),
                article.getViews(),
                article.getArticleType().name(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(article.getCreatedAt()),
                (long) article.getArticleVotes().size(),
                (long) article.getComments().size()
        );
    }
}
