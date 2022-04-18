package com.together.levelup.service;

import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class memberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        memberService.join(member1);
        memberService.join(member2);

        Member findMember1 = memberService.findOne(member1.getId());
        Member findMember2 = memberService.findOne(member2.getId());

        Assertions.assertThat(member1).isEqualTo(findMember1);
        Assertions.assertThat(member2).isEqualTo(findMember2);

    }

    @Test
    public void 중복_회원_테스트() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test0",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        memberService.join(member1);

        Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);
    }

}
