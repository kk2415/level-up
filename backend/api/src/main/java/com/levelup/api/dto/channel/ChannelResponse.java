package com.levelup.api.dto.channel;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.Getter;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Getter
public class ChannelResponse implements Serializable {

    private Long id;
    private boolean isManager;
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
                            boolean isManager,
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
        this.isManager = isManager;
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

    public static ChannelResponse from(Channel channel) {
        return new ChannelResponse(
                channel.getId(),
                false,
                channel.getName(),
                channel.getManagerNickname(),
                channel.getMemberMaxNumber(),
                channel.getDescription(),
                channel.getDescriptionSummary(),
                channel.getMemberCount(),
                channel.getCreatedAt().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                channel.getThumbnailImage().getStoreFileName(),
                channel.getExpectedStartDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                channel.getExpectedEndDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                channel.getCategory(),
                channel.getThumbnailImage()
        );
    }

    public static ChannelResponse of(Channel channel, boolean isManager) {
        return new ChannelResponse(
                channel.getId(),
                isManager,
                channel.getName(),
                channel.getManagerNickname(),
                channel.getMemberMaxNumber(),
                channel.getDescription(),
                channel.getDescriptionSummary(),
                channel.getMemberCount(),
                channel.getCreatedAt().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                channel.getThumbnailImage().getStoreFileName(),
                channel.getExpectedStartDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                channel.getExpectedEndDate().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                channel.getCategory(),
                channel.getThumbnailImage()
        );
    }
}
