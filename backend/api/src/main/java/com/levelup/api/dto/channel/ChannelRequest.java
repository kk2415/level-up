package com.levelup.api.dto.channel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ChannelRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private String name;

    @NotNull
    private Long limitedMemberNumber;

    @NotNull
    private String description;

    @NotNull
    private ChannelCategory category;

    @NotNull
    private UploadFile thumbnailImage;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expectedStartDate;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expectedEndDate;

    private List<UploadFile> uploadFiles;

    protected ChannelRequest() {}

    private ChannelRequest(
            Long memberId,
            String name,
            Long limitedMemberNumber,
            String description,
            ChannelCategory category,
            UploadFile thumbnailImage,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate,
            List<UploadFile> uploadFiles) {
        this.memberId = memberId;
        this.name = name;
        this.limitedMemberNumber = limitedMemberNumber;
        this.description = description;
        this.category = category;
        this.thumbnailImage = thumbnailImage;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
        this.uploadFiles = uploadFiles;
    }

    public static ChannelRequest of(
            Long memberId,
            String name,
            Long limitedMemberNumber,
            String description,
            ChannelCategory category,
            UploadFile thumbnailImage,
            LocalDate expectedStartDate,
            LocalDate expectedEndDate,
            List<UploadFile> uploadFiles) {
        return new ChannelRequest(
                memberId,
                name,
                limitedMemberNumber,
                description,
                category,
                thumbnailImage,
                expectedStartDate,
                expectedEndDate,
                uploadFiles);
    }

    public Channel toEntity(String managerName) {
        return Channel.builder()
                .name(name)
                .managerName(managerName)
                .description(description)
                .memberMaxNumber(limitedMemberNumber)
                .thumbnailImage(thumbnailImage)
                .category(category)
                .expectedStartDate(expectedStartDate)
                .expectedEndDate(expectedEndDate)
                .channelMembers(new ArrayList<>((int) (limitedMemberNumber + 1)))
                .channelPosts(new ArrayList<>())
                .files(new ArrayList<>())
                .build();
    }
}
