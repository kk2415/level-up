package com.levelup.core.repository.vote;

import com.levelup.core.domain.vote.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
    public Optional<List<Vote>> findByMemberIdAndArticleId(Long articleId, Long memberId) {
        String query = "select v from Vote v " +
                "join fetch v.article a " +
                "join fetch v.member m " +
                "where a.articleId = :articleId and m.id = :memberId";

        List<Vote> result = em.createQuery(query, Vote.class)
                .setParameter("articleId", articleId)
                .setParameter("memberId", memberId)
                .getResultList();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<List<Vote>> findByMemberIdAndCommentId(Long commentId, Long memberId) {
        String query = "select v from Vote v " +
                "join fetch v.comment c " +
                "join fetch v.member m " +
                "where c.id = :commentId and m.id = :memberId";

        List<Vote> result = em.createQuery(query, Vote.class)
                .setParameter("commentId", commentId)
                .setParameter("memberId", memberId)
                .getResultList();

        return Optional.ofNullable(result);
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
