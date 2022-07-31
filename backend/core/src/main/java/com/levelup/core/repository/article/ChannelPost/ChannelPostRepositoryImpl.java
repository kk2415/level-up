package com.levelup.core.repository.article.ChannelPost;

import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.Article.QChannelPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChannelPostRepositoryImpl implements ChannelPostQueryRepository {

    private final EntityManager em;

    @Override
    public Optional<ChannelPost> findNextByChannelIdAndArticleType(Long articleId, Long channelId, ArticleType articleType) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ChannelPost channelPost = queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .where(QChannelPost.channelPost.channel.id.eq(channelId))
                .where(QChannelPost.channelPost.articleType.eq(articleType))
                .where(QChannelPost.channelPost.articleId.gt(articleId))
                .orderBy(QChannelPost.channelPost.articleId.asc())
                .fetchFirst();

        return Optional.ofNullable(channelPost);
    }

    @Override
    public Optional<ChannelPost> findPrevChannelIdAndArticleType(Long articleId, Long channelId, ArticleType articleType) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ChannelPost channelPost = queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .where(QChannelPost.channelPost.channel.id.eq(channelId))
                .where(QChannelPost.channelPost.articleType.eq(articleType))
                .where(QChannelPost.channelPost.articleId.lt(articleId))
                .orderBy(QChannelPost.channelPost.articleId.desc())
                .fetchFirst();

        return Optional.ofNullable(channelPost);
    }

}
