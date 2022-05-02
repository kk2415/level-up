package com.together.levelup.dto.channel;

import com.together.levelup.domain.UploadFile;
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
