package com.levelup.core.repository.article;

import com.levelup.core.domain.article.*;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channelPost.QChannelPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleQueryRepository {

    private final EntityManager em;

    @Override
    public Optional<Article> findNextPageByArticleType(Long articleId, ArticleType articleType) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Article article = queryFactory.select(QArticle.article)
                .from(QArticle.article)
                .where(QArticle.article.articleType.eq(articleType))
                .where(QArticle.article.id.gt(articleId))
                .orderBy(QArticle.article.id.asc())
                .fetchFirst();

        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findPrevPageArticleType(Long articleId, ArticleType articleType) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Article article = queryFactory.select(QArticle.article)
                .from(QArticle.article)
                .where(QArticle.article.articleType.eq(articleType))
                .where(QArticle.article.id.lt(articleId))
                .orderBy(QArticle.article.id.desc())
                .fetchFirst();

        return Optional.ofNullable(article);
    }

    @Override
    public Optional<ChannelPost> findNextPageByChannelId(Long articleId, Long channelId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ChannelPost channelPost = queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .where(QChannelPost.channelPost.channel.id.eq(channelId))
                .where(QChannelPost.channelPost.id.gt(articleId))
                .orderBy(QChannelPost.channelPost.id.asc())
                .fetchFirst();

        return Optional.ofNullable(channelPost);
    }

    @Override
    public Optional<ChannelPost> findPrevPageChannelId(Long articleId, Long channelId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ChannelPost channelPost = queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .where(QChannelPost.channelPost.channel.id.eq(channelId))
                .where(QChannelPost.channelPost.id.lt(articleId))
                .orderBy(QChannelPost.channelPost.id.desc())
                .fetchFirst();

        return Optional.ofNullable(channelPost);
    }
}
