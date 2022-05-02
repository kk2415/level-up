package com.together.levelup.dto.channel;

import com.together.levelup.domain.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelFileResponse {

    private String fullPath;
    private UploadFile uploadFile;

}
