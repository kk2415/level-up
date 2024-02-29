package com.levelup.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.levelup")
@EnableJpaRepositories("com.levelup")
@EnableJpaAuditing(auditorAwareRef = "entityAuditorProvider")
@Configuration
public class JpaConfig {
}