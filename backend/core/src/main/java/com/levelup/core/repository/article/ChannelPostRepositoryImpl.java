package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.Article.QChannelPost;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.post.QPost;
import com.levelup.core.dto.post.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChannelPostRepositoryImpl implements ChannelPostQueryRepository {

    private final EntityManager em;

    @Override
    public Post findNextPage(Long postId, Long channelId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QPost.post)
                .from(QPost.post)
                .where(QPost.post.channel.id.eq(channelId))
                .where(QPost.post.id.gt(postId))
                .orderBy(QPost.post.id.asc())
                .fetchFirst();
    }

    @Override
    public Post findPrevPage(Long postId, Long channelId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QPost.post)
                .from(QPost.post)
                .where(QPost.post.channel.id.eq(channelId))
                .where(QPost.post.id.lt(postId))
                .orderBy(QPost.post.id.desc())
                .fetchFirst();
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
