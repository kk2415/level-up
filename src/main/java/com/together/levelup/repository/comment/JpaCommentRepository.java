package com.together.levelup.repository.comment;

import com.together.levelup.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    private final EntityManager em;

    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }

    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> findByMemberId(Long memberId) {
        String query = "select c from Comment c inner join c.member m where m.id = :memberId";

        return em.createQuery(query, Comment.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        String query = "select c from Comment c inner join c.post p where p.id = :postId";

        return em.createQuery(query, Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        Comment findComment = findById(id);
        em.remove(findComment);

    }

    @Override
    public Long countAll() {
        String query = "select count(c.id) from Comment c";

        return em.createQuery(query, Long.class).getResultList().get(0);
    }

}
