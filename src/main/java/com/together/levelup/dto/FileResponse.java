package com.together.levelup.dto;

import com.together.levelup.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {

    private String fullPath;
    private UploadFile uploadFile;

}
