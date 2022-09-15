package com.levelup.api.controller.v1.dto.response.vote;

import com.levelup.api.service.dto.vote.VoteDto;
import lombok.Getter;

@Getter
public class VoteResponse {

    private Long memberId;
    private Long targetId;
    private boolean isSuccessful;

    protected VoteResponse() {}

    private VoteResponse(Long memberId, Long targetId, boolean isSuccessful) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.isSuccessful = isSuccessful;
    }

    public static VoteResponse of(Long memberId, Long targetId, boolean isSuccessful) {
        return new VoteResponse(memberId, targetId, isSuccessful);
    }

    public static VoteResponse from(VoteDto dto) {
        return new VoteResponse(dto.getMemberId(), dto.getTargetId(), dto.isSuccessful());
    }
}
