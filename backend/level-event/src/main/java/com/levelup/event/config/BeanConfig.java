package com.levelup.event.config;

import com.levelup.event.events.EventPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    ApplicationContext applicationContext;

    public BeanConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public EventPublisher eventPublisher() {
        return new EventPublisher(applicationContext);
    }
}
