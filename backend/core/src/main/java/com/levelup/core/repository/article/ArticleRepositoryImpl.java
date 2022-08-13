package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.*;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channelPost.QChannelPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleQueryRepository {

    private final EntityManager em;
    private final JpaResultMapper jpaResultMapper;

//    @Override
//    public List<ArticlePagingDto> findByArticleType(ArticleType articleType, Pageable pageable) {
//        String sql =
//                "select a.article_id as articleId, " +
//                        "a.title, " +
//                        "a.views, " +
//                        "a.article_type as articleType, " +
//                        "a.created_at as createdAt, " +
//                        "m.nickname as writer, " +
//                        "(select count(1) from comment c where exists " +
//                            "(select 1 from article where c.article_id = a.article_id)) as commentCount, " +
//                        "(select count(1) from article_vote av where exists " +
//                            "(select 1 from article where av.article_id = a.article_id)) as voteCount " +
//                "from article a left outer join member m on a.member_id = m.member_id " +
//                "where article_type = '" + articleType.name() + "'" +
//                    " order by a." + PageableUtil.getProperty(pageable) + " " + PageableUtil.getDirection(pageable) +
//                    " limit " + pageable.getPageSize() +
//                    " offset " + pageable.getOffset();
//
//        Query nativeQuery = em.createNativeQuery(sql);
//        return jpaResultMapper.list(nativeQuery, ArticlePagingDto.class);
//    }

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
