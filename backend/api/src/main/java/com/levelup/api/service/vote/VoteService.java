package com.levelup.api.service.vote;


import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;

public interface VoteService {

    public VoteResponse save(CreateVoteRequest voteRequest);
    public void validate(Long memberId, Long targetId);
    public void delete(Long id);
}
