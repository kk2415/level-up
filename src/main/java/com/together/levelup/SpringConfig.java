package com.together.levelup;

import com.together.levelup.intercepter.LoginCheckIntercepter;
import com.together.levelup.repository.comment.CommentRepository;
import com.together.levelup.repository.comment.JpaCommentRepository;
import com.together.levelup.repository.member.JpaMemberRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.JpaPostRepository;
import com.together.levelup.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        registry.addInterceptor(new LoginCheckIntercepter())
                .order(1)
                .addPathPatterns("/member/logout", "/member/myPage",
                        "/post/edit/{postId}", "/post/create");
    }

}
