package com.levelup.api.util.file;

import com.levelup.core.domain.file.FileType;
import com.levelup.core.domain.file.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalFileStore implements FileStore {

    public final static String DEFAULT_IMAGE_NAME = "/images/member/AFF947XXQ-5554WSDQ12.png";

    @Value("${file.local_dir}")
    private String fileDir;

    @Override
    public String getFullPath(String filename) {
            return fileDir + filename;
    }

    @Override
    public boolean deleteFile(String storeFileName) {
        File file = new File(getFullPath(storeFileName));

        if (file.exists() && !storeFileName.equals(S3FileStore.DEFAULT_IMAGE_NAME)) {
            return file.delete();
        }
        return false;
    }

    @Override
    public List<UploadFile> storeFiles(FileType imageType, List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<UploadFile> uploadFiles = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(FileType.POST, multipartFile);
                uploadFiles.add(uploadFile);
            }
        }
        return uploadFiles;
    }

    @Override
    public UploadFile storeFile(FileType imageType, MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new UploadFile("default.png", DEFAULT_IMAGE_NAME);
        }

        String uploadFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(imageType, uploadFilename);
        String fullPath = getFullPath(storeFileName);

        multipartFile.transferTo(new File(fullPath));
        return new UploadFile(uploadFilename, storeFileName);
    }

    private String createStoreFileName(FileType imageType, String fileName) {
        UUID uuid = UUID.randomUUID();
        String ext = extractExt(fileName);
        String storedFileName = uuid + "." + ext;

        switch (imageType) {
            case MEMBER : return  "/images/member/" + storedFileName;
            case CHANNEL : return  "/images/channel/description/" + storedFileName;
            case CHANNEL_THUMBNAIL : return "/images/channel/thumbnail/" + storedFileName;
            case CHANNEL_NOTICE : return  "/images/channel_notice/" + storedFileName;
            case NOTICE : return  "/images/notice/" + storedFileName;
            case QNA : return  "/images/qna/" + storedFileName;
            default:  return  "/images/post/" + storedFileName;
        }
    }

    private String extractExt(String fileName) {
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }
}
