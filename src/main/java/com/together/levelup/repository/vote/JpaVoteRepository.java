package com.together.levelup.repository.vote;

import com.together.levelup.domain.vote.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaVoteRepository implements VoteRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public void save(Vote vote) {
        em.persist(vote);
    }


    /**
     * 조회
     * */
    @Override
    public Vote findById(Long id) {
        return em.find(Vote.class, id);
    }

    @Override
    public List<Vote> findByPostAndMember(Long postId, Long memberId) {
        String query = "select v from Vote v " +
                "join fetch v.post p " +
                "join fetch v.member m " +
                "where p.id = :postId and m.id = :memberId";

        return em.createQuery(query, Vote.class)
                .setParameter("postId", postId)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Vote> findByQnaAndMember(Long qnaId, Long memberId) {
        String query = "select v from Vote v " +
                "join fetch v.qna q " +
                "join fetch v.member m " +
                "where q.id = :qnaId and m.id = :memberId";

        return em.createQuery(query, Vote.class)
                .setParameter("qnaId", qnaId)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Vote> findByCommentAndMember(Long commentId, Long memberId) {
        String query = "select v from Vote v " +
                "join fetch v.comment c " +
                "join fetch v.member m " +
                "where c.id = :commentId and m.id = :memberId";

        return em.createQuery(query, Vote.class)
                .setParameter("commentId", commentId)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Vote> findByPostId(Long postId) {
        String query = "select v from Vote v join fetch v.post p where p.id = :postId";

        return em.createQuery(query, Vote.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public List<Vote> findByQnaId(Long qnaId) {
        String query = "select v from Vote v join fetch v.qna q where q.id = :qnaId";

        return em.createQuery(query, Vote.class)
                .setParameter("qnaId", qnaId)
                .getResultList();
    }

    @Override
    public List<Vote> findAll() {
        return em.createQuery("select v from Vote v", Vote.class)
                .getResultList();
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        Vote findVote = findById(id);
        em.remove(findVote);
    }

}
