package com.levelup.core.repository;

import com.levelup.core.CoreApplication;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest(classes = CoreApplication.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 멤버_생성() {
    }

}
