package com.levelup.image.domain.service.dto;

import com.levelup.common.domain.FileType;
import com.levelup.common.util.file.UploadFile;
import com.levelup.image.domain.entity.File;
import lombok.Getter;

@Getter
public class FileDto {

    private UploadFile uploadFile;
    private FileType fileType;
    private Long ownerId;

    protected FileDto() {}

    private FileDto(UploadFile uploadFile, FileType fileType, Long ownerId) {
        this.uploadFile = uploadFile;
        this.fileType = fileType;
        this.ownerId = ownerId;
    }

    public static FileDto from(File file) {
        return new FileDto(file.getUploadFile(), file.getFileType(), file.getOwnerId());
    }
}
