package com.levelup.api.dto.channel;

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

    private CreateChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
        this.limitedMemberNumber = channel.getMemberMaxNumber();
        this.managerName = channel.getManagerName();
        this.description = channel.getDescription();
    }

    public static CreateChannelResponse from(Channel channel) {
        return new CreateChannelResponse(channel);
    }
}
