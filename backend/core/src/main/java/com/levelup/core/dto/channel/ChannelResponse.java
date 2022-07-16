package com.levelup.core.dto.channel;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channel.ChannelMember;
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

    public ChannelResponse(Channel channel) {
        ChannelMember channelManager = channel.getChannelMembers().stream()
                .filter(member -> member.getIsManager())
                .findFirst().get();

        this.id = channel.getId();
        this.name = channel.getName();
        this.managerId = channelManager.getMember().getId();
        this.managerName = channelManager.getMember().getNickname();
        this.limitedMemberNumber = channel.getLimitedMemberNumber();
        this.description = channel.getDescription();
        this.thumbnailDescription = channel.getThumbnailDescription();
        this.postCount = channel.getPostCount();
        this.thumbnailImage = channel.getThumbnailImage();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channel.getCreateAt());
        this.category = channel.getCategory();

        this.memberCount = channel.getChannelMembers().stream()
                .filter(member -> !member.getIsWaitingMember())
                .count();

        if (channel.getThumbnailImage() != null) {
            this.storeFileName = channel.getThumbnailImage().getStoreFileName();
        }
    }
}
