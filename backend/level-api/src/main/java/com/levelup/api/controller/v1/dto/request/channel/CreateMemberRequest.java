package com.levelup.api.controller.v1.dto.request.channel;

import com.levelup.channel.domain.service.dto.ChannelMemberDto;
import lombok.Getter;

@Getter
public class CreateMemberRequest {

    private String memberEmail;
    private String memberNickname;
    private Boolean isManager;
    private Boolean isWaitingMember;

    protected CreateMemberRequest() {}

    private CreateMemberRequest(
            String memberEmail,
            String memberNickname,
            Boolean isManager,
            Boolean isWaitingMember)
    {
        this.memberEmail = memberEmail;
        this.memberNickname = memberNickname;
        this.isManager = isManager;
        this.isWaitingMember = isWaitingMember;
    }

    public static CreateMemberRequest of(
            String memberEmail,
            String memberNickname,
            Boolean isManager,
            Boolean isWaitingMember)
    {
        return new CreateMemberRequest(memberEmail, memberNickname, isManager, isWaitingMember);
    }

    public ChannelMemberDto toDto(Long memberId) {
        return ChannelMemberDto.of(null, memberId, memberEmail, memberNickname, isManager, isWaitingMember);
    }
}
