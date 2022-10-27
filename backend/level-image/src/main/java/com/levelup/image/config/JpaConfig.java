package com.levelup.image.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.levelup")
@EnableJpaRepositories("com.levelup")
@EnableJpaAuditing
@Configuration
public class JpaConfig {
}