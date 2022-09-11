package com.levelup.api.dto.channelMember;

import com.levelup.core.domain.channelMember.ChannelMember;
import lombok.Getter;

@Getter
public class ChannelMemberResponse {

    private Long channelMemberId;
    private Long memberId;
    private String email;
    private String nickname;
    private String storeFileName;
    private boolean isManager;

    protected ChannelMemberResponse() {}

    public ChannelMemberResponse(Long channelMemberId,
                                 Long memberId,
                                 String email,
                                 String nickname,
                                 String storeFileName,
                                 boolean isManager) {
        this.channelMemberId = channelMemberId;
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.storeFileName = storeFileName;
        this.isManager = isManager;
    }

    public static ChannelMemberResponse from(ChannelMember channelMember) {
        return new ChannelMemberResponse(
                channelMember.getId(),
                channelMember.getMember().getId(),
                channelMember.getMember().getEmail(),
                channelMember.getMember().getNickname(),
                channelMember.getMember().getProfileImage().getStoreFileName(),
                channelMember.getIsManager());
    }
}
