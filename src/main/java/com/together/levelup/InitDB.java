package com.together.levelup;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelMember;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.domain.member.Member;
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
            Member manager1 = Member.createMember("test0", "000000", "테스트네임0",
                    Gender.MALE, "19970927", "010-2354-9960");
            Member manager2 = Member.createMember("test1", "000000", "테스트네임1",
                    Gender.MALE, "19970927", "010-2354-9960");
            Member member1 = Member.createMember("test2", "000000", "테스트네임2",
                    Gender.MALE, "19970927", "010-2354-9960");
            em.persist(manager1);
            em.persist(manager2);
            em.persist(member1);

            Channel channel1 = Channel.createChannel(manager1, "축구를 잘하고싶은 사람들", 20L,
                    "안녕하세요!");
            Channel channel2 = Channel.createChannel(manager2, "스프링 학습방", 20L,
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
