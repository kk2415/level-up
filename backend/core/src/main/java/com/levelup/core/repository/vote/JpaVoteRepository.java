package com.levelup.core.repository.vote;

import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
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
    public Optional<List<Vote>> findByMemberAndTargetAndVoteType(Long memberId, Long targetId, VoteType voteType) {
        String query = "select v from Vote v " +
                "where v.memberId = :memberId and v.targetId = :targetId and v.voteType = :voteType";

        List<Vote> result = em.createQuery(query, Vote.class)
                .setParameter("memberId", memberId)
                .setParameter("targetId", targetId)
                .setParameter("voteType", voteType)
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
