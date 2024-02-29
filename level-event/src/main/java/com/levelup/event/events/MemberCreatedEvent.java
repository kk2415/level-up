package com.levelup.event.events;

import lombok.Getter;

@Getter
public class MemberCreatedEvent {

    private Long memberId;
    private String email;
    private String nickname;

    protected MemberCreatedEvent() {}

    private MemberCreatedEvent(Long memberId, String email, String nickname) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberCreatedEvent of(Long memberId, String email, String nickname) {
        return new MemberCreatedEvent(memberId, email, nickname);
    }
}
