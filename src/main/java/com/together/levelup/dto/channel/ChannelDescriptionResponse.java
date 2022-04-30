package com.together.levelup.dto.channel;

import com.together.levelup.domain.channel.ChannelCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelDescriptionResponse {

    private String name;
    private String description;
    private String thumbnailDescription;
    private String dateCreated;
    private String managerName;
    private Long memberCount;
    private Long limitedMemberNumber;
    private ChannelCategory category;

}
