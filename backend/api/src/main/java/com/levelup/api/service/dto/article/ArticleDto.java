package com.levelup.api.service.dto.article;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ArticleDto {

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

    public static ArticleDto of(Long articleId,
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

    public static ArticleDto from(Article article) {
        return new ArticleDto(
            article.getId(),
            article.getMember().getId(),
            article.getTitle(),
            article.getMember().getNickname(),
            article.getContent(),
            article.getCreatedAt(),
            (long) article.getArticleVotes().size(),
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
