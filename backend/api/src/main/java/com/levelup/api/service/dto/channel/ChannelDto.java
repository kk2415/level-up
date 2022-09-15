package com.levelup.api.service.dto.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Builder
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
    private String storeFileName;
    private ChannelCategory category;
    private UploadFile thumbnailImage;
    private LocalDateTime createdAt;
    private LocalDate expectedStartDate;
    private LocalDate expectedEndDate;

    protected ChannelDto() {}

    private ChannelDto(Long channelId,
                       Long managerId,
                       String name,
                       String managerNickname,
                       Long limitedMemberNumber,
                       String description,
                       String descriptionSummary,
                       Long memberCount,
                       String storeFileName,
                       ChannelCategory category,
                       UploadFile thumbnailImage,
                       LocalDateTime createdAt,
                       LocalDate expectedStartDate,
                       LocalDate expectedEndDate) {
        this.channelId = channelId;
        this.managerId = managerId;
        this.name = name;
        this.managerNickname = managerNickname;
        this.limitedMemberNumber = limitedMemberNumber;
        this.description = description;
        this.descriptionSummary = descriptionSummary;
        this.memberCount = memberCount;
        this.storeFileName = storeFileName;
        this.category = category;
        this.thumbnailImage = thumbnailImage;
        this.createdAt = createdAt;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
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
                channel.getThumbnailImage().getStoreFileName(),
                channel.getCategory(),
                channel.getThumbnailImage(),
                channel.getCreatedAt(),
                channel.getExpectedStartDate(),
                channel.getExpectedEndDate()
        );
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
