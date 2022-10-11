package com.levelup.event.events;

import lombok.Getter;

@Getter
public class MemberUpdatedEvent {

    private Long memberId;
    private String email;
    private String nickname;

    protected MemberUpdatedEvent() {}

    private MemberUpdatedEvent(Long memberId, String email, String nickname) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberUpdatedEvent of(Long memberId, String email, String nickname) {
        return new MemberUpdatedEvent(memberId, email, nickname);
    }
}
