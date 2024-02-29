package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.constant.ChannelCategory;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ChannelDto implements Serializable {

    private Long channelId;
    private Long managerId;
    private String name;
    private String managerNickname;
    private Long limitedMemberNumber;
    private String description;
    private String descriptionSummary;
    private Long memberCount;
    private ChannelCategory category;
    private LocalDateTime createdAt;
    private LocalDate expectedStartDate;
    private LocalDate expectedEndDate;

    protected ChannelDto() {}

    private ChannelDto(
            Long channelId,
            Long managerId,
            String name,
            String managerNickname,
            Long limitedMemberNumber,
            String description,
            String descriptionSummary,
            Long memberCount,
            ChannelCategory category,
            LocalDateTime createdAt,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate)
    {
        this.channelId = channelId;
        this.managerId = managerId;
        this.name = name;
        this.managerNickname = managerNickname;
        this.limitedMemberNumber = limitedMemberNumber;
        this.description = description;
        this.descriptionSummary = descriptionSummary;
        this.memberCount = memberCount;
        this.category = category;
        this.createdAt = createdAt;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
    }

    public ChannelDto (Channel channel, Long memberCount) {
        this.channelId = channel.getId();
        this.managerId = channel.getManagerId();
        this.name = channel.getName();
        this.managerNickname = channel.getManagerNickname();
        this.limitedMemberNumber = channel.getMemberMaxNumber();
        this.description = channel.getDescription();
        this.descriptionSummary = channel.getDescriptionSummary();
        this.memberCount = memberCount;
        this.category = channel.getCategory();
        this.createdAt = channel.getCreatedAt();
        this.expectedStartDate = channel.getExpectedStartDate();
        this.expectedEndDate = channel.getExpectedEndDate();
    }

    public static ChannelDto of(
            Long channelId,
            Long managerId,
            String name,
            String managerNickname,
            Long limitedMemberNumber,
            String description,
            String descriptionSummary,
            Long memberCount,
            ChannelCategory category,
            LocalDateTime createdAt,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate)
    {
        return new ChannelDto(
                channelId,
                managerId,
                name,
                managerNickname,
                limitedMemberNumber,
                description,
                descriptionSummary,
                memberCount,
                category,
                createdAt,
                expectedStartDate,
                expectedEndDate);
    }

    public static ChannelDto from(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getManagerId(),
                channel.getName(),
                channel.getManagerNickname(),
                channel.getMemberMaxNumber(),
                channel.getDescription(),
                channel.getDescriptionSummary(),
                channel.getMemberCount(),
                channel.getCategory(),
                channel.getCreatedAt(),
                channel.getExpectedStartDate(),
                channel.getExpectedEndDate()
        );
    }

    public Channel toEntity() {
        return Channel.of(
                null,
                description,
                name,
                limitedMemberNumber,
                category,
                expectedStartDate,
                expectedEndDate);
    }
}
