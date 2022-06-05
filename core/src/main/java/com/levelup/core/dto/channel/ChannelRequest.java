package com.levelup.core.dto.channel;

import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.UploadFile;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ChannelRequest {

    @NotNull
    private String memberEmail;

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

    private List<UploadFile> uploadFiles;

}
