package com.together.levelup.domain;

import com.together.levelup.domain.member.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    public final static String MEMBER_DEFAULT_IMAGE = "member/AFF947XXQ-5554WSDQ12.png";
    public final static String CHANNEL_DEFAULT_THUMBNAIL_IMAGE = "channel/thumbnail/rich-g5fba4398e_640.jpg";


    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
            return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<UploadFile> uploadFiles = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(ImageType.POST, multipartFile);
                uploadFiles.add(uploadFile);
            }
        }
        return uploadFiles;
    }

    public UploadFile storeFile(ImageType imageType, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String uploadFilename = multipartFile.getOriginalFilename();
        String storeFileName = getStoreFileName(imageType, uploadFilename);
        String fullPath = getFullPath(storeFileName);

        multipartFile.transferTo(new File(fullPath));
        return new UploadFile(uploadFilename, storeFileName);
    }

    private String getStoreFileName(ImageType imageType, String uploadFilename) {
        String storeFileName;

        if (imageType == ImageType.MEMBER) {
            storeFileName = "member/" + createStoreFileName(uploadFilename);
        }
        else if (imageType == ImageType.CHANNEL) {
            storeFileName = "channel/description/" + createStoreFileName(uploadFilename);
        }
        else if (imageType == ImageType.CHANNEL_THUMBNAIL) {
            storeFileName = "channel/thumbnail/" + createStoreFileName(uploadFilename);
        }
        else {
            storeFileName = "post/" + createStoreFileName(uploadFilename);
        }
        return storeFileName;
    }

    public String createStoreFileName(String fileName) {
        UUID uuid = UUID.randomUUID();
        String ext = extractExt(fileName);

        return uuid + "." + ext;
    }

    public String extractExt(String fileName) {
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }

}
