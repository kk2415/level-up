package com.levelup.api.util.file;

import com.levelup.core.domain.file.FileType;
import com.levelup.core.domain.file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStore {

    String getFullPath(String filename);
    boolean deleteFile(String storeFileName);
    List<UploadFile> storeFiles(FileType imageType, List<MultipartFile> multipartFiles) throws IOException;
    UploadFile storeFile(FileType imageType, MultipartFile multipartFile) throws IOException;
}
