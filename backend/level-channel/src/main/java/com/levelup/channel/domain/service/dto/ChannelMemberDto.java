package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.entity.ChannelMember;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChannelMemberDto {

    private Long channelMemberId;
    private Long memberId;
    private String email;
    private String nickname;
    private boolean isManager;
    private boolean isWaitingMember;

    protected ChannelMemberDto() {}

    private ChannelMemberDto(
            Long channelMemberId, 
            Long memberId, 
            String email, 
            String nickname, 
            boolean isManager,
            boolean isWaitingMember)
    {
        this.channelMemberId = channelMemberId;
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.isManager = isManager;
        this.isWaitingMember = isWaitingMember;
    }

    public static ChannelMemberDto of(
            Long channelMemberId,
            Long memberId,
            String email,
            String nickname,
            boolean isManager,
            boolean isWaitingMember)
    {
        return new ChannelMemberDto(channelMemberId, memberId, email, nickname, isManager, isWaitingMember);
    }

    public static ChannelMemberDto from(ChannelMember channelMember) {
        return new ChannelMemberDto(
                channelMember.getId(),
                channelMember.getMemberId(),
                channelMember.getEmail(),
                channelMember.getNickname(),
                channelMember.getIsManager(),
                channelMember.getIsWaitingMember());
    }
}
