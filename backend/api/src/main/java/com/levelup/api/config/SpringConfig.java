package com.levelup.api.config;

import com.levelup.api.intercepter.AdminCheckIntercepter;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.comment.JpaCommentRepository;
import com.levelup.core.repository.member.JpaMemberRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.post.JpaPostRepository;
import com.levelup.core.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String fileDir;

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

    @Bean
    public PostRepository postRepository() {
        return new JpaPostRepository(em);
    }

    @Bean
    public CommentRepository commentRepository() {
        return new JpaCommentRepository(em);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/Task/study/levelup/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginCheckIntercepter())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**", "/js/**", "/assets/**", "/error",
//                        "/", "/api/**",
//                        "/member/login", "/member/logout", "/member/create",
//                        "/channel/detail/{channelId}", "/channel/detail-description/{channelId}",
//                        "/notice", "/notice/{noticeId}",
//                        "/channel-notice/detail/{id}",
//                        "/post/detail/{postId}");
//
//        registry.addInterceptor(new LoginCheckApiIntercepter())
//                .order(2)
//                .addPathPatterns("/api/member/{email}/image",
//                        "/api/notice", "/api/notice/{noticeId}",
//                        "/api/channel-notice", "/api/channel-notice/{id}",
//                        "/api/comment", "/api/comment/reply",
//                        "/api/post", "/api/post/{postId}",
//                        "/api/channel/{channelId}/manager",
//                        "/api/channel",
//                        "/api/channel/{channelId}",
//                        "/api/channel/{channelId}/waiting-member",
//                        "/api/channel/{channelId}/member/{email}");

        registry.addInterceptor(new AdminCheckIntercepter())
                .order(3)
                .addPathPatterns("/**");
    }

}
