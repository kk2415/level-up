package com.together.levelup.dto.channel;

import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.file.UploadFile;
import lombok.Data;

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
    private String managerName;

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
