package com.levelup.article.domain.service.dto;

import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.ArticleType;
import com.levelup.member.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
public class ArticleDto implements Serializable {

    private Long articleId;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private ArticleType articleType;

    private ArticleDto(Long articleId,
                       Long memberId,
                       String title,
                       String writer,
                       String content,
                       LocalDateTime createdAt,
                       Long voteCount,
                       Long views,
                       Long commentCount,
                       ArticleType articleType) {
        this.articleId = articleId;
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

    public static ArticleDto of(
            Long articleId,
            Long memberId,
            String title,
            String writer,
            String content,
            LocalDateTime createdAt,
            Long voteCount,
            Long views,
            Long commentCount,
            ArticleType articleType)
    {
        return new ArticleDto(
                articleId,
                memberId,
                title,
                writer,
                content,
                createdAt,
                voteCount,
                views,
                commentCount,
                articleType
        );
    }

    public ArticleDto(Article article, Member member) {
        this.articleId = article.getId();
        this.memberId = member.getId();
        this.title = article.getTitle();
        this.writer = member.getNickname();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.voteCount = (long) article.getVotes().size();
        this.views = article.getViews();
        this.commentCount = (long) article.getComments().size();
        this.articleType = article.getArticleType();
    }

    public static ArticleDto from(Article article) {
        return new ArticleDto(
            article.getId(),
            article.getMember().getId(),
            article.getTitle(),
            article.getMember().getNickname(),
            article.getContent(),
            article.getCreatedAt(),
            (long) article.getVotes().size(),
            article.getViews(),
            (long) article.getComments().size(),
            article.getArticleType()
        );
    }

    public Article toEntity(Member member) {
        Article article = new Article();

        article.setMember(member);
        article.setTitle(title);
        article.setContent(content);
        article.setViews(0L);
        article.setArticleType(articleType);
        return article;
    }
}
