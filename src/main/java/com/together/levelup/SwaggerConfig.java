package com.together.levelup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final static Contact DEFAULT_CONTACT = new Contact("kyunkim", "", "kkh2415@naver.com");

    private final static ApiInfo DEFAULT_API_INFO = new ApiInfo("Level Up API Title",
            "Level Up Apllication REST API Service", "1.0", "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apapche.org/licenses/LICENSE-2.0", new ArrayList<>());

    private final static Set<String> DEFAULT_PRODUCES_AND_COMSUMES = new HashSet<>(
            Arrays.asList("application/json", "application/xml"));


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_COMSUMES)
                .consumes(DEFAULT_PRODUCES_AND_COMSUMES);
    }

}
