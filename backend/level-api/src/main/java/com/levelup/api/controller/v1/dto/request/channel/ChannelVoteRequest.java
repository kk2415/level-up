package com.levelup.api.controller.v1.dto.request.channel;

import com.levelup.channel.domain.entity.VoteType;
import com.levelup.channel.domain.service.dto.ChannelVoteDto;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ChannelVoteRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long channelId;

    @NotNull
    private Long targetId;

    @NotNull
    private VoteType voteType;

    protected ChannelVoteRequest() {}

    private ChannelVoteRequest(Long memberId, Long channelId, Long targetId, VoteType voteType) {
        this.memberId = memberId;
        this.channelId = channelId;
        this.targetId = targetId;
        this.voteType = voteType;
    }

    public static ChannelVoteRequest of(Long memberId, Long channelId, Long targetId, VoteType voteType) {
        return new ChannelVoteRequest(memberId, channelId, targetId, voteType);
    }

    public ChannelVoteDto toDto() {
        return ChannelVoteDto.of(memberId, channelId, targetId, voteType, false);
    }
}
