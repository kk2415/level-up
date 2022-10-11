package com.levelup.event.events;

import lombok.Getter;

@Getter
public class ChannelCreatedEvent {

    private Long channelId;
    private Long memberId;
    private String email;
    private String nickname;
    private String profileImage;

    protected ChannelCreatedEvent() {}

    private ChannelCreatedEvent(
            Long channelId,
            Long memberId,
            String email,
            String nickname,
            String profileImage)
    {
        this.channelId = channelId;
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static ChannelCreatedEvent of(
            Long channelId,
            Long memberId,
            String email,
            String nickname,
            String profileImage)
    {
        return new ChannelCreatedEvent(channelId, memberId, email, nickname, profileImage);
    }
}
