package com.together.levelup.repository;

import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class JpamemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member1;
    private Member member2;

    @BeforeEach
    public void before() {
        member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);
    }

    @Test
    public void memberRepositoryTest() {
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId());
        Member findMember2 = memberRepository.findById(member2.getId());

        Assertions.assertThat(member1).isEqualTo(findMember1);
        Assertions.assertThat(member2).isEqualTo(findMember2);
    }

}
