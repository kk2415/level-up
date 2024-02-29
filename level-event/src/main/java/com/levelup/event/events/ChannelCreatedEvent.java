package com.levelup.event.events;

import lombok.Getter;

@Getter
public class ChannelCreatedEvent {

    private Long memberId;

    protected ChannelCreatedEvent() {}

    private ChannelCreatedEvent(Long memberId) {
        this.memberId = memberId;
    }

    public static ChannelCreatedEvent of(Long memberId) {
        return new ChannelCreatedEvent(memberId);
    }
}
