package com.together.community;

import com.together.community.repository.member.JpaMemberRepository;
import com.together.community.repository.member.MemberRepository;
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

}
