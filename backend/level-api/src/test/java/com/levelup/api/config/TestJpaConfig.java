package com.levelup.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EntityScan("com.levelup")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@TestConfiguration
public class TestJpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("levelUp");
    }
}
