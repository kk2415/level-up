package com.levelup.common.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.levelup.common.util.file.FileStore;
import com.levelup.common.util.file.S3FileStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("commonBeanConfig")
public class BeanConfig {

    @Bean
    public FileStore fileStore() {
        return new S3FileStore(new AmazonS3Client());
    }
}
