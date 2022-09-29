package com.levelup.common.util.file;

import com.levelup.common.domain.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStore {

    String getFullPath(String filename);
    boolean deleteFile(String storeFileName);
    List<UploadFile> storeFiles(FileType imageType, List<MultipartFile> multipartFiles) throws IOException;
    UploadFile storeFile(FileType imageType, MultipartFile multipartFile) throws IOException;
}
