package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateChannelDto {

    private Long channelId;
    private Long managerId;
    private String channelName;
    private String managerNickname;
    private String managerEmail;
    private Long limitedMemberNumber;
    private String description;
    private String descriptionSummary;
    private Long memberCount;
    private ChannelCategory category;
    private LocalDate expectedStartDate;
    private LocalDate expectedEndDate;

    protected CreateChannelDto() {}

    public static CreateChannelDto of(
            Long channelId,
            Long managerId,
            String channelName,
            String managerNickname,
            String managerEmail,
            Long limitedMemberNumber,
            String description,
            String descriptionSummary,
            Long memberCount,
            ChannelCategory category,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate)
    {
        return new CreateChannelDto(
                channelId,
                managerId,
                channelName,
                managerNickname,
                managerEmail,
                limitedMemberNumber,
                description,
                descriptionSummary,
                memberCount,
                category,
                expectedStartDate,
                expectedEndDate);
    }

    public Channel toEntity() {
        return Channel.of(
                null,
                description,
                channelName,
                limitedMemberNumber,
                category,
                expectedStartDate,
                expectedEndDate);
    }
}
