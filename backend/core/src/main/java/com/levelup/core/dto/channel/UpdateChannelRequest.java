package com.levelup.core.dto.channel;

import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateChannelRequest {

    @NotNull
    private String name;

    @NotNull
    private Long limitedMemberNumber;

    @NotNull
    private String description;

    @NotNull
    private ChannelCategory category;

    @NotNull
    private String thumbnailDescription;

    @NotNull
    private UploadFile thumbnailImage;

}
