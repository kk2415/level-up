package com.levelup.core.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ChannelResponse {

    private Long id;
    private String name;
    private Long limitedMemberNumber;
    private String managerName;
    private String description;
    private String thumbnailDescription;
    private Long memberCount;

}
