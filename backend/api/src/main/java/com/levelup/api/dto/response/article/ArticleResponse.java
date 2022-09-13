package com.levelup.api.dto.response.article;

import com.levelup.api.dto.service.article.ArticleDto;
import com.levelup.core.DateFormat;
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

    private ArticleResponse(Long id,
                           Long memberId,
                           String title,
                           String writer,
                           String content,
                           String dateCreated,
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
        this.dateCreated = dateCreated;
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
