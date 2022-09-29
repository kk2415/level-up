package com.levelup.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@EnableGlobalMethodSecurity(securedEnabled = true) //secured 애노테이션 활성화
@EnableCaching
@SpringBootApplication(scanBasePackages = "com.levelup")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
