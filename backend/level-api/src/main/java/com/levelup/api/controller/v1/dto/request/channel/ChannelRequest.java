package com.levelup.api.controller.v1.dto.request.channel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.common.util.file.UploadFile;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
public class ChannelRequest {

    @NotNull
    private String name;

    @NotNull
    private Long limitedMemberNumber;

    @NotNull
    private String description;

    @NotNull
    private ChannelCategory category;

    private UploadFile thumbnailImage;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expectedStartDate;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expectedEndDate;

    protected ChannelRequest() {}

    private ChannelRequest(
            String name,
            Long limitedMemberNumber,
            String description,
            ChannelCategory category,
            UploadFile thumbnailImage,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate) {
        this.name = name;
        this.limitedMemberNumber = limitedMemberNumber;
        this.description = description;
        this.category = category;
        this.thumbnailImage = thumbnailImage;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
    }

    public static ChannelRequest of(String name,
                                    Long limitedMemberNumber,
                                    String description,
                                    ChannelCategory category,
                                    UploadFile thumbnailImage,
                                    LocalDate expectedStartDate,
                                    LocalDate expectedEndDate)
    {
        return new ChannelRequest(
                name,
                limitedMemberNumber,
                description,
                category,
                thumbnailImage,
                expectedStartDate,
                expectedEndDate);
    }

    public ChannelDto toDto(Long managerId, String managerNickname) {
        return ChannelDto.builder()
                .channelId(null)
                .managerId(managerId)
                .name(name)
                .managerNickname(managerNickname)
                .limitedMemberNumber(limitedMemberNumber)
                .description(description)
                .descriptionSummary(null)
                .memberCount(0L)
                .storeFileName(null)
                .category(category)
                .thumbnailImage(null)
                .createdAt(null)
                .expectedStartDate(expectedStartDate)
                .expectedEndDate(expectedEndDate)
                .build();
    }

    public ChannelDto toDto() {
        return ChannelDto.builder()
                .channelId(null)
                .managerId(null)
                .name(name)
                .managerNickname(null)
                .limitedMemberNumber(limitedMemberNumber)
                .description(description)
                .descriptionSummary(null)
                .memberCount(0L)
                .storeFileName(thumbnailImage.getStoreFileName())
                .category(category)
                .thumbnailImage(thumbnailImage)
                .createdAt(null)
                .expectedStartDate(expectedStartDate)
                .expectedEndDate(expectedEndDate)
                .build();
    }
}
