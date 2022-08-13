package com.levelup.api.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.levelup.core.domain.file.ImageType;
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
public class S3FileStore {

    public final static String DEFAULT_IMAGE = "thumbnail/fb23d674-c0ed-417c-b732-6743b8989406.png";
    private final AmazonS3Client amazonS3Client;

    @Value("${file.linux_local_dir}")
    private String LOCAL_FILE_DIR;

    @Value("${file.s3_dir}")
    private String S3_FILE_DIR;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String getFullPath(String filename) {
            return S3_FILE_DIR + filename;
    }

    public List<UploadFile> storeFiles(ImageType imageType, List<MultipartFile> multipartFiles) throws IOException {
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
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new UploadFile("default.png", DEFAULT_IMAGE);
        }

        String uploadFilename = multipartFile.getOriginalFilename();
        String storeFileName = getStoreFileName(imageType, uploadFilename);

        File file = new File(LOCAL_FILE_DIR + "/" + uploadFilename);
        multipartFile.transferTo(file);

        amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        deleteLocalFile(file);

        return new UploadFile(uploadFilename, storeFileName);
    }

    private String getStoreFileName(ImageType imageType, String uploadFilename) {
        String storeFileName;

        if (imageType == ImageType.MEMBER) {
            storeFileName = "profile/" + createStoreFileName(uploadFilename);
        }
        else {
            storeFileName = "thumbnail/" + createStoreFileName(uploadFilename);
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

    public void deleteS3File(String storeFileName) {
        amazonS3Client.deleteObject(bucket, S3_FILE_DIR + storeFileName);
    }

    public void deleteLocalFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

}
