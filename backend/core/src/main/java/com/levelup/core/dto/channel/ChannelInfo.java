package com.levelup.core.dto.channel;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channelMember.ChannelMember;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ChannelInfo {

    private String channelName;
    private String manager;
    private String date;
    private Long memberCount;
    private Long waitingMemberCount;
    private Long postCount;
    private String thumbnail;

    public ChannelInfo(Channel channel) {
        this.channelName = channel.getName();
        this.manager = channel.getManagerName();
        this.date = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channel.getCreatedAt());
        this.memberCount = channel.getChannelMembers().stream()
                .filter(member -> !member.getIsWaitingMember())
                .count();

        this.waitingMemberCount = channel.getChannelMembers().stream()
                .filter(ChannelMember::getIsWaitingMember)
                .count();

        this.postCount = (long) channel.getChannelPosts().size();
        this.thumbnail = channel.getThumbnailImage().getStoreFileName();
    }
}
