package com.levelup.api.dto.channel;

import com.levelup.core.domain.file.UploadFile;
import lombok.Data;

@Data
public class ChannelFileResponse {

    private String fullPath;
    private UploadFile uploadFile;

    private ChannelFileResponse(String fullPath, UploadFile uploadFile) {
        this.fullPath = fullPath;
        this.uploadFile = uploadFile;
    }

    public static ChannelFileResponse of(String fullPath, UploadFile uploadFile) {
        return new ChannelFileResponse(fullPath, uploadFile);
    }
}
