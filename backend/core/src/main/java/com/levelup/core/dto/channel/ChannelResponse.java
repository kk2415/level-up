package com.levelup.core.dto.channel;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelResponse implements Serializable {

    private Long id;
    private Long managerId;
    private String name;
    private String managerName;
    private Long limitedMemberNumber;
    private String description;
    private String thumbnailDescription;
    private Long memberCount;
    private Long postCount;
    private String dateCreated;
    private String storeFileName;
    private ChannelCategory category;
    private UploadFile thumbnailImage;

    private ChannelResponse(Channel channel) {
        channel.getChannelMembers().stream()
                .filter(ChannelMember::getIsManager).findFirst()
                .ifPresent(channelManager -> {
                    this.managerId = channelManager.getMember().getId();
                });

        this.id = channel.getId();
        this.name = channel.getName();
        this.managerName = channel.getManagerName();
        this.limitedMemberNumber = channel.getMemberMaxNumber();
        this.description = channel.getDescription();
        this.thumbnailDescription = channel.getMainDescription();
        this.postCount = (long) channel.getChannelPosts().size();
        this.thumbnailImage = channel.getThumbnailImage();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channel.getCreatedAt());
        this.category = channel.getCategory();
        this.memberCount = channel.getChannelMembers().stream()
                .filter(member -> !member.getIsWaitingMember())
                .count();
        this.storeFileName = channel.getThumbnailImage().getStoreFileName();
    }

    public static ChannelResponse from(Channel channel) {
        return new ChannelResponse(channel);
    }
}
