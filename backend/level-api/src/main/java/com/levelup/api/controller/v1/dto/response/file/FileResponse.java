package com.levelup.api.controller.v1.dto.response.file;

import com.levelup.common.util.file.UploadFile;
import lombok.Getter;

@Getter
public class FileResponse {

    private String fullPath;
    private UploadFile uploadFile;

    protected FileResponse() {}
}
