package com.together.community.repository.post;

import com.together.community.domain.Post;
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

    public Long countAll() {
        String queryString = "select count(p.id) from Post p";

        return em.createQuery(queryString, Long.class).getResultList().get(0);
    }

}
