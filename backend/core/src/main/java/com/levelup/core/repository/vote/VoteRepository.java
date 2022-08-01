package com.levelup.core.repository.vote;


import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByMemberIdAndTargetIdAndVoteType(Long memberId, Long targetId, VoteType voteType);
}
