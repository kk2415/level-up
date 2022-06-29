package com.levelup.core.dto.vote;

import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteResponse {

    private Long memberId;
    private Long targetId;
    private VoteType voteType;

    public VoteResponse(Vote vote) {
        this.memberId = vote.getMemberId();
        this.targetId = vote.getTargetId();
        this.voteType = vote.getVoteType();
    }

}
