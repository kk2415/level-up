package com.levelup.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EntityScan("com.levelup.core")
@EnableJpaRepositories("com.levelup.core")
//@EnableGlobalMethodSecurity(securedEnabled = true) //secured 애노테이션 활성화
@SpringBootApplication(scanBasePackages = "com.levelup")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
