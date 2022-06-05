package com.levelup.core.dto.channel;

import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelFileResponse {

    private String fullPath;
    private UploadFile uploadFile;

}
