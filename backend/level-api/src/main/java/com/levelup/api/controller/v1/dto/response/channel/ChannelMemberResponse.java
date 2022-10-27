package com.levelup.api.controller.v1.dto.response.channel;

import com.levelup.channel.domain.service.dto.ChannelMemberDto;
import com.levelup.channel.domain.entity.ChannelMember;
import lombok.Getter;

@Getter
public class ChannelMemberResponse {

    private Long channelMemberId;
    private Long memberId;
    private String email;
    private String nickname;
    private boolean isManager;

    protected ChannelMemberResponse() {}

    public ChannelMemberResponse(
            Long channelMemberId,
            Long memberId,
            String email,
            String nickname,
            boolean isManager)
    {
        this.channelMemberId = channelMemberId;
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.isManager = isManager;
    }

    public static ChannelMemberResponse from(ChannelMemberDto dto) {
        return new ChannelMemberResponse(
                dto.getChannelMemberId(),
                dto.getMemberId(),
                dto.getEmail(),
                dto.getNickname(),
                dto.isManager()
        );
    }

    public static ChannelMemberResponse from(ChannelMember channelMember) {
        return new ChannelMemberResponse(
                channelMember.getId(),
                channelMember.getMemberId(),
                channelMember.getEmail(),
                channelMember.getNickname(),
                channelMember.getIsManager());
    }
}
