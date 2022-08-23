package com.levelup.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@EnableCaching
@Configuration
public class CacheConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    private static final Integer TTL = 30 * 1 * 1;

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisCacheConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisCacheConnectionFactory)
                .cacheDefaults(redisCacheConfiguration())
                .build();
    }

    @Bean
    public RedisConnectionFactory redisCacheConnectionFactory(@Value("${spring.redis.host}") String redisHost,
                                                              @Value("${spring.redis.port}") int redisPort,
                                                              @Value("${spring.redis.password}") String redisPassword) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(name -> name + ":")
                .entryTtl(Duration.ofSeconds(TTL))
                .serializeKeysWith(fromSerializer(new StringRedisSerializer()));
    }

    private ObjectMapper objectMapper() {
        // jackson 2.10이상 3.0버전까지 적용 가능
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        return JsonMapper.builder()
                .polymorphicTypeValidator(ptv)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .addModule(new JavaTimeModule())
                .activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
                .build();
    }
}
