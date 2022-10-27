package com.levelup.api.controller.v1.dto.response.channel;

import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.common.util.DateFormat;
import com.levelup.channel.domain.entity.ChannelCategory;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ChannelResponse {

    private Long id;
    private Long managerId;
    private String name;
    private String managerName;
    private Long limitedMemberNumber;
    private String description;
    private String descriptionSummary;
    private Long memberCount;
    private ChannelCategory category;
    private String createdAt;
    private String expectedStartDate;
    private String expectedEndDate;

    protected ChannelResponse() {}

    private ChannelResponse(
            Long id,
            Long managerId,
            String name,
            String managerName,
            Long limitedMemberNumber,
            String description,
            String descriptionSummary,
            Long memberCount,
            String createdAt,
            String expectedStartDate,
            String expectedEndDate,
            ChannelCategory category)
    {
        this.id = id;
        this.managerId = managerId;
        this.name = name;
        this.managerName = managerName;
        this.limitedMemberNumber = limitedMemberNumber;
        this.description = description;
        this.descriptionSummary = descriptionSummary;
        this.memberCount = memberCount;
        this.createdAt = createdAt;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
        this.category = category;
    }

    public static ChannelResponse from(ChannelDto dto) {
        return new ChannelResponse(
                dto.getChannelId(),
                dto.getManagerId(),
                dto.getName(),
                dto.getManagerNickname(),
                dto.getLimitedMemberNumber(),
                dto.getDescription(),
                dto.getDescriptionSummary(),
                dto.getMemberCount(),
                dto.getCreatedAt().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT)),
                dto.getExpectedStartDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                dto.getExpectedEndDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                dto.getCategory()
        );
    }
}
