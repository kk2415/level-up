package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.*;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.post.QPost;
import com.levelup.core.dto.post.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
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
                .where(QArticle.article.articleId.gt(articleId))
                .orderBy(QArticle.article.articleId.asc())
                .fetchFirst();

        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findPrevPageArticleType(Long articleId, ArticleType articleType) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Article article = queryFactory.select(QArticle.article)
                .from(QArticle.article)
                .where(QArticle.article.articleType.eq(articleType))
                .where(QArticle.article.articleId.lt(articleId))
                .orderBy(QArticle.article.articleId.desc())
                .fetchFirst();

        return Optional.ofNullable(article);
    }

    @Override
    public Optional<ChannelPost> findNextPageByChannelId(Long articleId, Long channelId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ChannelPost channelPost = queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .where(QChannelPost.channelPost.channel.id.eq(channelId))
                .where(QChannelPost.channelPost.articleId.gt(articleId))
                .orderBy(QChannelPost.channelPost.articleId.asc())
                .fetchFirst();

        return Optional.ofNullable(channelPost);
    }

    @Override
    public Optional<ChannelPost> findPrevPageChannelId(Long articleId, Long channelId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ChannelPost channelPost = queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .where(QChannelPost.channelPost.channel.id.eq(channelId))
                .where(QChannelPost.channelPost.articleId.lt(articleId))
                .orderBy(QChannelPost.channelPost.articleId.desc())
                .fetchFirst();

        return Optional.ofNullable(channelPost);
    }

    @Override
    public List<ChannelPost> findByChannelId(Long channelId, SearchCondition postSearch) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .join(QChannelPost.channelPost.channel)
                .where(QChannelPost.channelPost.channel.id.eq(channelId), equalQuery(postSearch))
                .orderBy(QChannelPost.channelPost.createAt.desc())
                .fetch();
    }

    @Override
    public List<ChannelPost> findByChannelId(Long channelId, int page, int postCount, SearchCondition postSearch) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        int firstPage = (page - 1) * postCount; //0, 10, 20, 30

        return queryFactory.select(QChannelPost.channelPost)
                .from(QChannelPost.channelPost)
                .join(QChannelPost.channelPost.channel)
                .where(QChannelPost.channelPost.channel.id.eq(channelId), equalQuery(postSearch))
                .orderBy(QChannelPost.channelPost.createAt.desc())
                .offset(firstPage)
                .limit(postCount)
                .fetch();
    }

    private BooleanExpression equalQuery(SearchCondition postSearch) {
        if (postSearch == null || postSearch.getField() == null || postSearch.getQuery() == null) {
            return null;
        }

        if (postSearch.getField().equals("title")) {
            return QPost.post.title.contains(postSearch.getQuery());
        }
        return QPost.post.writer.contains(postSearch.getQuery());
    }

}
