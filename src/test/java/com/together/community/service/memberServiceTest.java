package com.together.community.service;

import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class memberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입() {

        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);

        memberService.join(member1);
        memberService.join(member2);

        Member findMember1 = memberService.findOne(member1.getId());
        Member findMember2 = memberService.findOne(member2.getId());

        Assertions.assertThat(member1).isEqualTo(findMember1);
        Assertions.assertThat(member2).isEqualTo(findMember2);

    }

    @Test
    public void 중복_회원_테스트() {
        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test0", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);

        memberService.join(member1);

        Assertions.assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);
    }

    private Member getMember(String loginId, String birthday, String email, String name, Gender gender) {
        Member member1 = new Member();
        member1.setLoginId(loginId);
        member1.setPassword("0000");
        member1.setBirthday(birthday);
        member1.setEmail(email);
        member1.setDateCreated(LocalDateTime.now());
        member1.setGender(gender);
        member1.setPhone("010-2354-9960");
        member1.setName(name);
        return member1;
    }

}
