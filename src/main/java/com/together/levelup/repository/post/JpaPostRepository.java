package com.together.levelup.repository.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.post.QPost;
import com.together.levelup.dto.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaPostRepository implements PostRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public void save(Post post) {
        em.persist(post);
    }


    /**
     * 조회
     * */
    @Override
    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    @Override
    public List<Post> findByTitle(String title) {
        return em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public List<Post> findByWriter(String writer) {
        return em.createQuery("select p from Post p where p.writer = :writer", Post.class)
                .setParameter("writer", writer)
                .getResultList();
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Override
    public List<Post> findByMemberId(Long memberId) {
        String query = "select p from Post p join p.member m where m.id = :memberId "
                + "order by p.dateCreated desc";

        return em.createQuery(query, Post.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Post findNextPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QPost.post)
                .from(QPost.post)
                .where(QPost.post.id.gt(id))
                .orderBy(QPost.post.id.asc())
                .fetchFirst();
    }

    @Override
    public Post findPrevPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QPost.post)
                .from(QPost.post)
                .where(QPost.post.id.lt(id))
                .orderBy(QPost.post.id.desc())
                .fetchFirst();
    }

    @Override
    public List<Post> findByChannelId(Long channelId, int page, PostSearch postSearch) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        int firstPage = (page - 1) * 10; //0, 10, 20, 30
        int lastPage = page * 10; //9, 19, 29, 39

        return queryFactory.select(QPost.post)
                .from(QPost.post)
                .join(QPost.post.channel)
                .where(QPost.post.channel.id.eq(channelId), equalQuery(postSearch))
                .orderBy(QPost.post.dateCreated.desc())
                .offset(firstPage)
                .limit(lastPage)
                .fetch();
    }

    private BooleanExpression equalQuery(PostSearch postSearch) {
        if (postSearch == null || postSearch.getField() == null || postSearch.getQuery() == null) {
            return null;
        }

        if (postSearch.getField().equals("title")) {
            return QPost.post.title.contains(postSearch.getQuery());
        }
        return QPost.post.writer.contains(postSearch.getQuery());
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        Post findMember = findById(id);
        em.remove(findMember);
    }

}
