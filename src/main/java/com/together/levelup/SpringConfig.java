package com.together.levelup;

import com.together.levelup.repository.comment.CommentRepository;
import com.together.levelup.repository.comment.JpaCommentRepository;
import com.together.levelup.repository.member.JpaMemberRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.JpaPostRepository;
import com.together.levelup.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

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

}
