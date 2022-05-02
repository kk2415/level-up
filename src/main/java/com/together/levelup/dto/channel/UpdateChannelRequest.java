package com.together.levelup.dto.channel;

import com.together.levelup.domain.UploadFile;
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
    private String thumbnailDescription;

    @NotNull
    private UploadFile thumbnailImage;

}
