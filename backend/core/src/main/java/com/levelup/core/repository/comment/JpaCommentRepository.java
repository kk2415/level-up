package com.levelup.core.repository.comment;

import com.levelup.core.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    private final EntityManager em;


    /***
     * 생성
     */
    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }


    /***
     * 조회
     */
    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> findReplyById(Long commentId) {
        String query = "select c from Comment c " +
                "join fetch c.member m " +
                "where c.parent.id = :commentId";

        return em.createQuery(query, Comment.class)
                .setParameter("commentId", commentId)
                .getResultList();
    }

    @Override
    public List<Comment> findByMemberId(Long memberId) {
        String query = "select c from Comment c inner join c.member m where m.id = :memberId";

        return em.createQuery(query, Comment.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Comment> findByArticleId(Long articleId) {
        String query = "select c from Comment c join fetch c.article a " +
                "join fetch c.member m " +
                "where a.articleId = :articleId order by c.id asc";

        return em.createQuery(query, Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }


    /**
     * 삭제
     */
    @Override
    public void delete(Long id) {
        Comment findComment = findById(id);
        em.remove(findComment);
    }

}
