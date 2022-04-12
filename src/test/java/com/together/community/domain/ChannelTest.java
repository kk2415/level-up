package com.together.community.domain;

import com.together.community.domain.channel.Channel;
import com.together.community.domain.channel.ChannelMember;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class ChannelTest {

    @Autowired
    private EntityManager em;

    @Test
    public void 채널_등록_테스트() {
        Member member1 = Member.createMember("test0", "naver.com",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1", "naver.com",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        em.persist(member1);
        em.persist(member2);

        Channel channel = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모");
        em.persist(channel);
    }

    @Test
    public void 채널_멤버_추가() {
        Member member1 = Member.createMember("test0", "naver.com",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1", "naver.com",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        em.persist(member1);
        em.persist(member2);

        Channel channel = Channel.createChannel(member1,"모두모두 모여라 요리왕", 20L, "요리 친목도모");
        em.persist(channel);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2);
        channel.addMember(channelMember1, channelMember2);
    }

}
