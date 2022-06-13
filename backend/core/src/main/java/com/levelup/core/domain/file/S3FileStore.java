package com.levelup.core.domain.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    public final static String MEMBER_DEFAULT_IMAGE = "/images/member/AFF947XXQ-5554WSDQ12.png";
    public final static String CHANNEL_DEFAULT_THUMBNAIL_IMAGE = "/images/channel/thumbnail/rich-g5fba4398e_640.jpg";
    private final AmazonS3Client amazonS3Client;

    @Value("${file.dir}")
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
            return new UploadFile("default.png", MEMBER_DEFAULT_IMAGE);
        }

        String uploadFilename = multipartFile.getOriginalFilename();
        String storeFileName = getStoreFileName(imageType, uploadFilename);

        File file = new File(LOCAL_FILE_DIR + "/" + uploadFilename);
        multipartFile.transferTo(file);

        System.out.println(storeFileName);
        amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        if (file.delete()) {
            log.info("Success delete file");
        }
        else {
            log.error("Fail delete file");
        }

        return new UploadFile(uploadFilename, storeFileName);
    }

    private String getStoreFileName(ImageType imageType, String uploadFilename) {
        String storeFileName;

        // 반드시 나중에 리팩토링...
        if (imageType == ImageType.MEMBER) {
            storeFileName = "profile/" + createStoreFileName(uploadFilename);
        }
        else if (imageType == ImageType.CHANNEL) {
            storeFileName = "/images/channel/description/" + createStoreFileName(uploadFilename);
        }
        else if (imageType == ImageType.CHANNEL_THUMBNAIL) {
            storeFileName = "/images/channel/thumbnail/" + createStoreFileName(uploadFilename);
        }
        else if (imageType == ImageType.CHANNEL_NOTICE) {
            storeFileName = "/images/channel_notice/" + createStoreFileName(uploadFilename);
        }
        else if (imageType == ImageType.NOTICE) {
            storeFileName = "/images/notice/" + createStoreFileName(uploadFilename);
        }
        else if (imageType == ImageType.QNA) {
            storeFileName = "/images/qna/" + createStoreFileName(uploadFilename);
        }
        else {
            storeFileName = "/images/post/" + createStoreFileName(uploadFilename);
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
