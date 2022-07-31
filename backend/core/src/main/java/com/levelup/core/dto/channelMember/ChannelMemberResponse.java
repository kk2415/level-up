package com.levelup.core.dto.channelMember;

import com.levelup.core.domain.channel.ChannelMember;
import lombok.Data;

@Data
public class ChannelMemberResponse {

    private Long channelMemberId;
    private Long memberId;
    private String email;
    private boolean isManager;


    private ChannelMemberResponse(ChannelMember channelMember) {
        this.channelMemberId = channelMember.getId();
        this.memberId = channelMember.getMember().getId();
        this.email = channelMember.getMember().getEmail();
        this.isManager = channelMember.getIsManager();
    }

    public static ChannelMemberResponse from(ChannelMember channelMember) {
        return new ChannelMemberResponse(channelMember);
    }
}
