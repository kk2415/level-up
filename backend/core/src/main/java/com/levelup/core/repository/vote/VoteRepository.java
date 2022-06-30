package com.levelup.core.repository.vote;


import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;

import java.util.List;
import java.util.Optional;


public interface VoteRepository {

    void save(Vote notice);
    Vote findById(Long id);
    Optional<List<Vote>> findByMemberAndTargetAndVoteType(Long memberId, Long targetId, VoteType voteType);
    List<Vote> findAll();
    void delete(Long id);

}
