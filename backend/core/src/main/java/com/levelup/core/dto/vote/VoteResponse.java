package com.levelup.core.dto.vote;

import com.levelup.core.domain.vote.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class VoteResponse {

    private Long ownerId;
    private VoteType voteType;

}
