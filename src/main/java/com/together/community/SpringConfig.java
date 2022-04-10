package com.together.community;

import com.together.community.repository.comment.CommentRepository;
import com.together.community.repository.comment.JpaCommentRepository;
import com.together.community.repository.member.JpaMemberRepository;
import com.together.community.repository.member.MemberRepository;
import com.together.community.repository.post.JpaPostRepository;
import com.together.community.repository.post.PostRepository;
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
