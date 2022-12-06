package com.levelup.image.controller.v1.dto;

import com.levelup.common.domain.constant.FileType;
import com.levelup.common.util.file.UploadFile;
import com.levelup.image.domain.service.dto.FileDto;
import lombok.Getter;

@Getter
public class FileResponse {

    private UploadFile uploadFile;
    private FileType fileType;
    private Long ownerId;

    protected FileResponse() {}

    private FileResponse(UploadFile uploadFile, FileType fileType, Long ownerId) {
        this.uploadFile = uploadFile;
        this.fileType = fileType;
        this.ownerId = ownerId;
    }

    public static FileResponse from(FileDto dto) {
        return new FileResponse(dto.getUploadFile(), dto.getFileType(), dto.getOwnerId());
    }
}
