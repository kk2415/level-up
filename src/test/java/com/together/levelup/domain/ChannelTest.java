package com.together.levelup.domain;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.channel.ChannelMember;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.member.UploadFile;
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
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960", null);
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

        em.persist(member1);
        em.persist(member2);

        Channel channel = Channel.createChannel(member1, "모두모두 모여라 요리왕", 20L, "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
        em.persist(channel);
    }

    @Test
    public void 채널_멤버_추가() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MALE, "970927", "010-2354-9960",null );
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMALE, "020509", "010-5874-3699", null);

        em.persist(member1);
        em.persist(member2);

        Channel channel = Channel.createChannel(member1,"모두모두 모여라 요리왕", 20L, "요리 친목도모", ChannelCategory.STUDY, new UploadFile("default", FileStore.CHANNEL_DEFAULT_IMAGE));
        em.persist(channel);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2);
        channel.addMember(channelMember1, channelMember2);
    }

}
