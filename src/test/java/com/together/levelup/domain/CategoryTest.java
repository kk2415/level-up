package com.together.levelup.domain;

import com.together.levelup.domain.category.Category;
import com.together.levelup.domain.category.CategoryChannel;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelMember;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class CategoryTest {

    @Autowired
    private EntityManager em;

    @Test
    public void 카테고리_테스트() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        em.persist(member1);
        em.persist(member2);

        Channel channel = Channel.createChannel(member1,"모두모두 모여라 요리왕", 20L, "요리 친목도모");
        em.persist(channel);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2);
        channel.addMember(channelMember1, channelMember2);

        CategoryChannel categoryChannel = CategoryChannel.createCategoryChannel(channel);

        Category category1 = Category.createCategory("스포츠", categoryChannel);
        em.persist(category1);
    }

    @Test
    void 부모_자식_테스트() {
        Member member1 = Member.createMember("test0",
                "0000", "김경희", Gender.MAIL, "970927", "010-2354-9960");
        Member member2 = Member.createMember("test1",
                "0000", "이예지", Gender.FEMAIL, "020509", "010-5874-3699");

        em.persist(member1);
        em.persist(member2);

        Channel channel1 = Channel.createChannel(member1,"모두모두 모여라 요리왕", 20L, "요리 친목도모");
        Channel channel2 = Channel.createChannel(member1,"내일은 축구 천재", 20L, "축구 실력 향상 모임");
        em.persist(channel1);
        em.persist(channel2);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2);
        channel1.addMember(channelMember1, channelMember2);

        CategoryChannel categoryChannel1 = CategoryChannel.createCategoryChannel(channel1);
        CategoryChannel categoryChannel2 = CategoryChannel.createCategoryChannel(channel2);

        Category category1 = Category.createCategory("스포츠", categoryChannel1);
        Category category2 = Category.createCategory("요리", categoryChannel2);

        category1.addChildCategory(category2);

        em.persist(category1);
        em.persist(category2);
    }

}
