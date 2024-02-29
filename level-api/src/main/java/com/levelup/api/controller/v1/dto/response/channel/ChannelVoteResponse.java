package com.levelup.api.controller.v1.dto.response.channel;

import com.levelup.channel.domain.service.dto.ChannelVoteDto;
import lombok.Getter;

@Getter
public class ChannelVoteResponse {

    private Long memberId;
    private Long channelId;
    private Long targetId;
    private boolean isSuccessful;

    protected ChannelVoteResponse() {}

    private ChannelVoteResponse(Long memberId, Long channelId, Long targetId, boolean isSuccessful) {
        this.memberId = memberId;
        this.channelId = channelId;
        this.targetId = targetId;
        this.isSuccessful = isSuccessful;
    }

    public static ChannelVoteResponse of(Long memberId, Long channelId, Long targetId, boolean isSuccessful) {
        return new ChannelVoteResponse(memberId, channelId, targetId, isSuccessful);
    }

    public static ChannelVoteResponse from(ChannelVoteDto dto) {
        return new ChannelVoteResponse(dto.getMemberId(), dto.getChannelId(), dto.getTargetId(), dto.isSuccessful());
    }
}
