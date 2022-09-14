package com.levelup.api.dto.response.channel;

import com.levelup.api.dto.service.channel.ChannelDto;
import com.levelup.core.DateFormat;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.Getter;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Getter
public class ChannelResponse implements Serializable {

    private Long id;
    private Long managerId;
    private String name;
    private String managerName;
    private Long limitedMemberNumber;
    private String description;
    private String descriptionSummary;
    private Long memberCount;
    private String storeFileName;
    private ChannelCategory category;
    private UploadFile thumbnailImage;
    private String dateCreated;
    private String expectedStartDate;
    private String expectedEndDate;

    protected ChannelResponse() {}

    private ChannelResponse(Long id,
                            Long managerId,
                            String name,
                            String managerName,
                            Long limitedMemberNumber,
                            String description,
                            String descriptionSummary,
                            Long memberCount,
                            String dateCreated,
                            String storeFileName,
                            String expectedStartDate,
                            String expectedEndDate,
                            ChannelCategory category,
                            UploadFile thumbnailImage) {
        this.id = id;
        this.managerId = managerId;
        this.name = name;
        this.managerName = managerName;
        this.limitedMemberNumber = limitedMemberNumber;
        this.description = description;
        this.descriptionSummary = descriptionSummary;
        this.memberCount = memberCount;
        this.dateCreated = dateCreated;
        this.storeFileName = storeFileName;
        this.expectedStartDate = expectedStartDate;
        this.expectedEndDate = expectedEndDate;
        this.category = category;
        this.thumbnailImage = thumbnailImage;
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
                dto.getThumbnailImage().getStoreFileName(),
                dto.getExpectedStartDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                dto.getExpectedEndDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                dto.getCategory(),
                dto.getThumbnailImage()
        );
    }
}
