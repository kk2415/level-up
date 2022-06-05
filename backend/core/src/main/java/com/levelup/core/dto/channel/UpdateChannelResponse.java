package com.levelup.core.dto.channel;

import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateChannelResponse {

    private String name;
    private Long limitedMemberNumber;
    private String description;
    private String thumbnailDescription;
    private UploadFile thumbnailImage;

}
