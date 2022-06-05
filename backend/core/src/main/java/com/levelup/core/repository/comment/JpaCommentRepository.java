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
        String query = "select c from Comment c where c.parent.id = :commentId";

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
    public List<Comment> findByPostId(Long postId) {
        String query = "select c from Comment c join fetch c.post p where p.id = :postId order by c.dateCreated";

        return em.createQuery(query, Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public List<Comment> findByNoticeId(Long noticeId) {
        String query = "select c from Comment c join fetch c.notice n where n.id = :noticeId order by c.dateCreated";

        return em.createQuery(query, Comment.class)
                .setParameter("noticeId", noticeId)
                .getResultList();
    }

    @Override
    public List<Comment> findByChannelNoticeId(Long channelNoticeId) {
        String query = "select c from Comment c join fetch c.channelNotice cn where cn.id = :channelNoticeId order by c.dateCreated";

        return em.createQuery(query, Comment.class)
                .setParameter("channelNoticeId", channelNoticeId)
                .getResultList();
    }

    @Override
    public List<Comment> findByQnaId(Long qnaId) {
        String query = "select c from Comment c join fetch c.qna q where q.id = :qnaId order by c.dateCreated";

        return em.createQuery(query, Comment.class)
                .setParameter("qnaId", qnaId)
                .getResultList();
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public Long countAll() {
        String query = "select count(c.id) from Comment c";

        return em.createQuery(query, Long.class).getResultList().get(0);
    }

    /***
     * 삭제
     */
    @Override
    public void delete(Long id) {
        Comment findComment = findById(id);
        em.remove(findComment);
    }

}
