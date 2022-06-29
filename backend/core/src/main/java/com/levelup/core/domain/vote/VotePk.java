package com.levelup.core.domain.vote;

import lombok.Data;

import java.io.Serializable;

@Data
public class VotePk implements Serializable {
    private Long memberId;
    private Long targetId;
    private VoteType voteType;
}
