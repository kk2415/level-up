package com.levelup.core.domain.channel;

import com.levelup.core.DateFormat;
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
        this.date = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channel.getCreateAt());
        this.memberCount = channel.getChannelMembers().stream()
                .filter(member -> !member.getIsWaitingMember())
                .count();

        this.waitingMemberCount = channel.getChannelMembers().stream()
                .filter(member -> member.getIsWaitingMember())
                .count();

        this.postCount = channel.getPostCount();
        this.thumbnail = channel.getThumbnailImage().getStoreFileName();
    }

}
