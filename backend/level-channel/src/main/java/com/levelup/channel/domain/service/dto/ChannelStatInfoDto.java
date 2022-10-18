package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.common.util.DateFormat;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ChannelStatInfoDto {

    private String channelName;
    private String manager;
    private String date;
    private Long memberCount;
    private Long waitingMemberCount;
    private Long postCount;
    private String thumbnail;

    protected ChannelStatInfoDto() {}

    private ChannelStatInfoDto(
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

    public static ChannelStatInfoDto from(Channel channel) {
        return new ChannelStatInfoDto(
            channel.getName(),
            channel.getManagerNickname(),
            DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(channel.getCreatedAt()),
            channel.getChannelMembers().stream()
                    .filter(member -> !member.getIsWaitingMember())
                    .count(),
            channel.getChannelMembers().stream()
                    .filter(ChannelMember::getIsWaitingMember)
                    .count(),
            (long) channel.getChannelArticles().size(),
            channel.getThumbnail().getStoreFileName()
        );
    }
}
