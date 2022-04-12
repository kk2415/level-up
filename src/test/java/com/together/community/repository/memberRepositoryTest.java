package com.together.community.repository;

import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import com.together.community.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class memberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void memberRepositoryTest() {
        Member member1 = Member.createMember("test0", "naver.com",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1", "naver.com",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId());
        Member findMember2 = memberRepository.findById(member2.getId());

        Assertions.assertThat(member1).isEqualTo(findMember1);
        Assertions.assertThat(member2).isEqualTo(findMember2);
    }

}
