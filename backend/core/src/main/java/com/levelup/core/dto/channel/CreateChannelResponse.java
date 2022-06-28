package com.levelup.core.dto.channel;

import com.levelup.core.domain.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CreateChannelResponse {

    private Long id;
    private String name;
    private Long limitedMemberNumber;
    private String managerName;
    private String description;

    public CreateChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
        this.limitedMemberNumber = channel.getLimitedMemberNumber();
        this.managerName = channel.getManagerName();
        this.description = channel.getDescription();
    }
}
