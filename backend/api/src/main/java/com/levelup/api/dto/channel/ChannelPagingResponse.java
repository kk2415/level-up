package com.levelup.api.dto.channel;

import com.levelup.core.dto.channel.ChannelPagingDto;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ChannelPagingResponse implements Serializable {

    private Long channelId;
    private String channelName;
    private Long memberMaxNumber;
    private String mainDescription;
    private String managerName;
    private String storeFileName;
    private Long memberCount;

    protected ChannelPagingResponse() {}

    private ChannelPagingResponse(Long channelId,
                                  String channelName,
                                  Long memberMaxNumber,
                                  String mainDescription,
                                  String managerName,
                                  String storeFileName,
                                  Long memberCount) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.memberMaxNumber = memberMaxNumber;
        this.mainDescription = mainDescription;
        this.managerName = managerName;
        this.storeFileName = storeFileName;
        this.memberCount = memberCount;
    }

    public static ChannelPagingResponse from(ChannelPagingDto channelPagingDto) {
        return new ChannelPagingResponse(
                channelPagingDto.getChannelId(),
                channelPagingDto.getChannelName(),
                channelPagingDto.getMemberMaxNumber(),
                channelPagingDto.getMainDescription(),
                channelPagingDto.getManagerName(),
                channelPagingDto.getStoreFileName(),
                channelPagingDto.getMemberCount()
        );
    }
}
