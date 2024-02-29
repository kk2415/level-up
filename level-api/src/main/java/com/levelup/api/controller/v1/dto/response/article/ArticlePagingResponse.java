package com.levelup.api.controller.v1.dto.response.article;

import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.common.util.DateFormat;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.service.dto.ArticlePagingDto;
import lombok.Getter;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Getter
public class ArticlePagingResponse implements Serializable {

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

    public static ArticlePagingResponse from(ArticleDto dto) {
        return new ArticlePagingResponse(
                dto.getArticleId(),
                dto.getTitle(),
                dto.getWriter(),
                dto.getViews(),
                dto.getArticleType().name(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getVoteCount(),
                dto.getCommentCount()
        );
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
                article.getWriter().getNickname(),
                article.getViews(),
                article.getArticleType().name(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(article.getCreatedAt()),
                (long) article.getVotes().size(),
                (long) article.getComments().size()
        );
    }
}
