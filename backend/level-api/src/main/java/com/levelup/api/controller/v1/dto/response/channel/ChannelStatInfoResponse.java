package com.levelup.api.controller.v1.dto.response.channel;

import com.levelup.channel.domain.service.dto.ChannelStatInfoDto;
import lombok.Getter;

@Getter
public class ChannelStatInfoResponse {

    private String channelName;
    private String manager;
    private String date;
    private Long memberCount;
    private Long waitingMemberCount;
    private Long postCount;
    private String thumbnail;

    protected ChannelStatInfoResponse() {}

    private ChannelStatInfoResponse(
            String channelName,
            String manager,
            String date,
            Long memberCount,
            Long waitingMemberCount,
            Long postCount,
            String thumbnail)
    {
        this.channelName = channelName;
        this.manager = manager;
        this.date = date;
        this.memberCount = memberCount;
        this.waitingMemberCount = waitingMemberCount;
        this.postCount = postCount;
        this.thumbnail = thumbnail;
    }

    public static ChannelStatInfoResponse from(ChannelStatInfoDto dto) {
        return new ChannelStatInfoResponse(
                dto.getChannelName(),
                dto.getManager(),
                dto.getDate(),
                dto.getMemberCount(),
                dto.getWaitingMemberCount(),
                dto.getPostCount(),
                dto.getThumbnail()
        );
    }
}
