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

    @Override
    public void save(Post post) {
        em.persist(post);
    }

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
    public void delete(Long id) {
        Post findMember = findById(id);
        em.remove(findMember);
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
    public List<Post> findByChannelId(Long channelId) {
        String query = "select p from Post p join p.channel c where c.id = :channelId order by p.dateCreated";

        return em.createQuery(query, Post.class)
                .setParameter("channelId", channelId)
                .getResultList();
    }

    @Override
    public List<Post> findByChannelId(Long channelId, int page) {
        int firstPage = (page - 1) * 10; //0, 10, 20, 30
        int lastPage = page * 10; //9, 19, 29, 39

        String query = "select p from Post p join p.channel c where c.id = :channelId order by p.dateCreated";

        return em.createQuery(query, Post.class)
                .setParameter("channelId", channelId)
                .setFirstResult(firstPage)
                .setMaxResults(lastPage)
                .getResultList();
    }

//    public List<Post> findByChannelId(Long channelId, int page, PostSearch postSearch) {
//        int firstPage = (page - 1) * 10; //0, 10, 20, 30
//        int lastPage = page * 10; //9, 19, 29, 39
//
//        if (postSearch == null || postSearch.getField() == null || postSearch.getQuery() == null) {
//            return findByChannelId(channelId, page);
//        }
//
//        String sqlQuery = "select p from Post p join p.channel c where c.id = :channelId and p.writer like CONCAT('%',:query,'%')";
//        if (postSearch.getField().equals("title")) {
//            sqlQuery = "select p from Post p join p.channel c where c.id = :channelId and p.title like CONCAT('%',:query,'%')";
//        }
//
//        return em.createQuery(sqlQuery, Post.class)
//                .setParameter("channelId", channelId)
//                .setParameter("query", postSearch.getQuery())
//                .setFirstResult(firstPage)
//                .setMaxResults(lastPage)
//                .getResultList();
//    }

    @Override
    public List<Post> findByChannelId(Long channelId, int page, PostSearch postSearch) {
        int firstPage = (page - 1) * 10; //0, 10, 20, 30
        int lastPage = page * 10; //9, 19, 29, 39

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

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

    public Long countAll() {
        String queryString = "select count(p.id) from Post p";

        return em.createQuery(queryString, Long.class).getResultList().get(0);
    }

}
