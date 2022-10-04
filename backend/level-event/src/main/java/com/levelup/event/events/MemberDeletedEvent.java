package com.levelup.event.events;

import lombok.Getter;

@Getter
public class MemberDeletedEvent {

    private Long deletedMemberId;
    private String deletedMemberEmail;
    private String deletedMemberNickname;

    protected MemberDeletedEvent() {}

    private MemberDeletedEvent(Long deletedMemberId, String deletedMemberEmail, String deletedMemberNickname) {
        this.deletedMemberId = deletedMemberId;
        this.deletedMemberEmail = deletedMemberEmail;
        this.deletedMemberNickname = deletedMemberNickname;
    }

    public static MemberDeletedEvent of(Long deletedMemberId, String deletedMemberEmail, String deletedMemberNickname) {
        return new MemberDeletedEvent(deletedMemberId, deletedMemberEmail, deletedMemberNickname);
    }
}
