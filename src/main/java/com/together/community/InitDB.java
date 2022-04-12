package com.together.community;

import com.together.community.domain.channel.Channel;
import com.together.community.domain.channel.ChannelMember;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import com.together.community.service.ChannelService;
import com.together.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initDb();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void initDb() {
            Member manager = Member.createMember("test0", "naver.com", "000000", "테스트네임0",
                    Gender.MAIL, "19970927", "010-2354-9960");
            Member member1 = Member.createMember("test1", "naver.com", "000000", "테스트네임1",
                    Gender.MAIL, "19970927", "010-2354-9960");
            em.persist(manager);
            em.persist(member1);

            Channel channel1 = Channel.createChannel(manager, "축구를 잘하고싶은 사람들", 20L,
                    "안녕하세요!");
            Channel channel2 = Channel.createChannel(manager, "스프링 학습방", 20L,
                    "스프링 초보들 다 모여라!");
            em.persist(channel1);
            em.persist(channel2);

            ChannelMember channelMember1 = ChannelMember.createChannelMember(member1);
            ChannelMember channelMember2 = ChannelMember.createChannelMember(member1);

            channel1.addMember(channelMember1);
            channel2.addMember(channelMember2);
        }
    }
}
