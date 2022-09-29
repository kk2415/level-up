package com.levelup.api.controller.v1.dto.response.article;

import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.common.util.DateFormat;
import com.levelup.article.domain.ArticleType;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ArticleResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private String createdAt;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private ArticleType articleType;

    protected ArticleResponse() {}

    private ArticleResponse(Long id,
                           Long memberId,
                           String title,
                           String writer,
                           String content,
                           String createdAt,
                           Long voteCount,
                           Long views,
                           Long commentCount,
                           ArticleType articleType)
    {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
        this.articleType = articleType;
    }

    public static ArticleResponse from(ArticleDto dto) {
        return new ArticleResponse(
                dto.getArticleId(),
                dto.getMemberId(),
                dto.getTitle(),
                dto.getWriter(),
                dto.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getVoteCount(),
                dto.getViews(),
                dto.getCommentCount(),
                dto.getArticleType()
        );
    }
}
