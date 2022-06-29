package com.levelup.core.dto.channel;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ChannelResponse {

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

    public ChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
        this.limitedMemberNumber = channel.getLimitedMemberNumber();
        this.managerName = channel.getManagerName();
        this.managerId = channel.getManager().getId();
        this.description = channel.getDescription();
        this.thumbnailDescription = channel.getThumbnailDescription();
        this.memberCount = channel.getMemberCount();
        this.postCount = channel.getPostCount();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channel.getCreateAt());

        if (channel.getThumbnailImage() != null) {
            this.storeFileName = channel.getThumbnailImage().getStoreFileName();
        }
    }
}
