package com.together.community.domain;

import com.together.community.domain.category.Category;
import com.together.community.domain.category.CategoryChannel;
import com.together.community.domain.channel.Channel;
import com.together.community.domain.channel.ChannelMember;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class CategoryTest {

    @Autowired
    private EntityManager em;

    @Test
    public void 카테고리_테스트() {

        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);

        em.persist(member1);
        em.persist(member2);

        Channel channel = Channel.createChannel("모두모두 모여라 요리왕", "김탁구", 20L, "요리 친목도모");
        em.persist(channel);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2);
        channel.addMember(channelMember1, channelMember2);

        CategoryChannel categoryChannel = CategoryChannel.createCategoryChannel(channel);

        Category category1 = Category.createCategory("스포츠", categoryChannel);
        em.persist(category1);

    }

    @Test
    @Commit
    void 부모_자식_테스트() {

        Member member1 = getMember("test0", "1997", "kkh2415@naver.com", "김경희", Gender.MAIL);
        Member member2 = getMember("test1", "2002", "goodnight@naver.com", "박병로", Gender.MAIL);

        em.persist(member1);
        em.persist(member2);

        Channel channel1 = Channel.createChannel("모두모두 모여라 요리왕", "김탁구", 20L, "요리 친목도모");
        Channel channel2 = Channel.createChannel("내일은 축구 천재", "박지성", 20L, "축구 실력 향상 모임");
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
