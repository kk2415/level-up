package com.levelup.core.dto.vote;

import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CreateVoteRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long targetId;

    @NotNull
    private VoteType voteType;

    public Vote toEntity() {
        return Vote.builder()
                .memberId(memberId)
                .targetId(targetId)
                .voteType(voteType)
                .build();
    }

}
