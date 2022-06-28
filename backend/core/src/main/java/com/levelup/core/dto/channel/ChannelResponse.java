package com.levelup.core.dto.channel;

import com.levelup.core.domain.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelResponse {

    private Long id;
    private String name;
    private Long limitedMemberNumber;
    private String managerName;
    private Long managerId;
    private String description;
    private String thumbnailDescription;
    private Long memberCount;
    private Long postCount;
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

        if (channel.getThumbnailImage() != null) {
            this.storeFileName = channel.getThumbnailImage().getStoreFileName();
        }
    }
}
