package com.levelup.api.config;

import com.levelup.core.domain.base.Auditor;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.comment.JpaCommentRepository;
import com.levelup.core.repository.member.JpaMemberRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Autowired
    private EntityManager em;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }

//    @Bean
//    public PostRepository postRepository() {
//        return new JpaPostRepository(em);
//    }

    @Bean
    public CommentRepository commentRepository() {
        return new JpaCommentRepository(em);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new Auditor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/Task/study/levelup/images/");
    }

}
