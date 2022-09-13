package com.levelup.api.dto.service.chanelMember;

import com.levelup.api.dto.response.channelMember.ChannelMemberResponse;
import com.levelup.core.domain.channelMember.ChannelMember;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChannelMemberDto {

    private Long channelMemberId;
    private Long memberId;
    private String email;
    private String nickname;
    private String storeFileName;
    private boolean isManager;
    
    protected ChannelMemberDto() {}

    public ChannelMemberDto(
            Long channelMemberId, 
            Long memberId, 
            String email, 
            String nickname, 
            String storeFileName, 
            boolean isManager)
    {
        this.channelMemberId = channelMemberId;
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.storeFileName = storeFileName;
        this.isManager = isManager;
    }

    public static ChannelMemberDto from(ChannelMember channelMember) {
        return new ChannelMemberDto(
                channelMember.getId(),
                channelMember.getMember().getId(),
                channelMember.getMember().getEmail(),
                channelMember.getMember().getNickname(),
                channelMember.getMember().getProfileImage().getStoreFileName(),
                channelMember.getIsManager());
    }
}
