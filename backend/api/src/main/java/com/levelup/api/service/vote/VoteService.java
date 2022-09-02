package com.levelup.api.service.vote;


import com.levelup.api.dto.vote.CreateVoteRequest;
import com.levelup.api.dto.vote.VoteResponse;

public interface VoteService {

    public VoteResponse save(CreateVoteRequest voteRequest);
    public void validate(Long memberId, Long targetId);
    public void delete(Long id);
}
