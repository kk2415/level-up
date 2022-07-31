package com.levelup.core.repository.vote;


import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select v from Vote v " +
                    "where v.memberId = :memberId and v.targetId = :targetId and v.voteType = :voteType")
    Optional<List<Vote>> findByMemberAndTargetAndVoteType(Long memberId, Long targetId, VoteType voteType);
}
