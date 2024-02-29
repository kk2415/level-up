package com.levelup.common.util.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.levelup.common.domain.constant.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Profile("prod")
@Component
@RequiredArgsConstructor
public class S3FileStore implements FileStore {

    public final static String DEFAULT_IMAGE_NAME = "thumbnail/fb23d674-c0ed-417c-b732-6743b8989406.png";

    @Value("${file.storage.dir}")
    private String LOCAL_FILE_DIR;

    @Value("${file.storage.dir}")
    private String S3_FILE_DIR;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket; //S3 버킷 이름

    private final AmazonS3Client amazonS3Client;

    @Override
    public String getFullPath(String filename) {
            return S3_FILE_DIR + filename;
    }

    @Override
    public boolean deleteFile(String storeFileName) {
        if (!storeFileName.equals(S3FileStore.DEFAULT_IMAGE_NAME)) {
            amazonS3Client.deleteObject(bucket, S3_FILE_DIR + storeFileName);
            return true;
        }
        return false;
    }

    @Override
    public List<UploadFile> storeFiles(FileType fileType, List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<UploadFile> uploadFiles = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(FileType.ARTICLE, multipartFile);
                uploadFiles.add(uploadFile);
            }
        }
        return uploadFiles;
    }

    @Override
    public UploadFile storeFile(FileType fileType, MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new UploadFile("default.png", DEFAULT_IMAGE_NAME);
        }

        String uploadFilename = multipartFile.getOriginalFilename();
        String storedFileName = createStoredFileName(fileType, uploadFilename);

        File tempFile = storeTempLocalFile(multipartFile, uploadFilename);

        amazonS3Client.putObject(new PutObjectRequest(bucket, storedFileName, tempFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        deleteLocalFile(tempFile);

        return new UploadFile(uploadFilename, storedFileName);
    }

    private String createStoredFileName(FileType imageType, String fileName) {
        UUID uuid = UUID.randomUUID();
        String ext = extractExt(fileName);
        String storedFileName = uuid + "." + ext;

        String storeFileName = "thumbnail/" + storedFileName;
        if (imageType == FileType.MEMBER) {
            storeFileName = "profile/" + storedFileName;
        }

        return storeFileName;
    }

    private String extractExt(String fileName) {
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }

    private File storeTempLocalFile(MultipartFile multipartFile, String uploadFilename) throws IOException {
        File file = new File(LOCAL_FILE_DIR + "/" + uploadFilename);
        multipartFile.transferTo(file);
        return file;
    }

    private void deleteLocalFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}
