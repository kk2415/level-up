package com.levelup.api.controller.v1.dto.response.file;

import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {

    private String fullPath;
    private UploadFile uploadFile;

}
