package com.levelup.api.dto.channel;

import com.levelup.core.util.HtmlParser;
import com.levelup.core.dto.channel.ChannelPagingDto;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ChannelPagingResponse implements Serializable {

    private Long channelId;
    private String channelName;
    private Long memberMaxNumber;
    private String descriptionSummary;
    private String managerName;
    private String storeFileName;
    private Long memberCount;

    protected ChannelPagingResponse() {}

    private ChannelPagingResponse(Long channelId,
                                  String channelName,
                                  Long memberMaxNumber,
                                  String descriptionSummary,
                                  String managerName,
                                  String storeFileName,
                                  Long memberCount) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.memberMaxNumber = memberMaxNumber;
        this.descriptionSummary = descriptionSummary;
        this.managerName = managerName;
        this.storeFileName = storeFileName;
        this.memberCount = memberCount;
    }

    public static ChannelPagingResponse from(ChannelPagingDto channelPagingDto) {
        String descriptionSummary = HtmlParser.removeTag(
                channelPagingDto.getDescription().substring(0, channelPagingDto.getDescription().length()));
        if (descriptionSummary.length() > 10) {
            descriptionSummary = descriptionSummary.substring(0, 10) + "...";
        }

        return new ChannelPagingResponse(
                channelPagingDto.getChannelId(),
                channelPagingDto.getChannelName(),
                channelPagingDto.getMemberMaxNumber(),
                descriptionSummary,
                channelPagingDto.getManagerName(),
                channelPagingDto.getStoreFileName(),
                channelPagingDto.getMemberCount()
        );
    }
}
