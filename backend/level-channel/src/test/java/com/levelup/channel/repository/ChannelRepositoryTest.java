package com.levelup.channel.repository;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.member.MemberApplication;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("채널 멤버 레포지토리 테스트")
@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ChannelApplicationTest.class, MemberApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelRepositoryTest extends TestSupporter {

    @Autowired MemberRepository memberRepository;
    @Autowired ChannelRepository channelRepository;
    @Autowired ChannelMemberRepository channelMemberRepository;

    @BeforeEach
    public void before() {
        memberRepository.deleteAll();
        channelRepository.deleteAll();
        channelMemberRepository.deleteAll();
    }

    @DisplayName("채널 카테고리별 조회")
    @Test
    void findByCategoryAndOrderByMemberCount() {
        Member manager1 = createMember("manager1", "manager1");
        Member manager2 = createMember("manager2", "manager2");
        Member manager3 = createMember("manager3", "manager3");
        Member member1 = createMember("member1", "member1");
        Member member2 = createMember("member2", "member2");
        Member member3 = createMember("member3", "member3");
        Member member4 = createMember("member4", "member4");
        Member member5 = createMember("member5", "member5");
        memberRepository.save(manager1);
        memberRepository.save(manager2);
        memberRepository.save(manager3);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        ChannelMember channelManager1 = createChannelMember(manager1, true, false);
        ChannelMember channelManager2 = createChannelMember(manager2, true, false);
        ChannelMember channelManager3 = createChannelMember(manager3, true, false);

        Channel channel1 = createChannel(channelManager1, "test channel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(channelManager2, "test channel2", ChannelCategory.STUDY);
        Channel channel3 = createChannel(channelManager3, "test channel3", ChannelCategory.STUDY);
        ChannelMember channelMember1 = createChannelMember(member1, channel1, false);
        ChannelMember channelMember2 = createChannelMember(member2, channel1, false);
        ChannelMember channelMember3 = createChannelMember(member3, channel2, false);
        ChannelMember channelMember4 = createChannelMember(member4, channel2, false);
        ChannelMember channelMember5 = createChannelMember(member5, channel2, false);
        ChannelMember channelMember6 = createChannelMember(member5, channel3, false);
        channelRepository.save(channel1);
        channelRepository.save(channel2);
        channelRepository.save(channel3);

        List<Channel> channels = channelRepository.findByCategoryAndOrderByMemberCount(ChannelCategory.STUDY.name());
        assertThat(channels.get(0)).isEqualTo(channel2);
        assertThat(channels.get(1)).isEqualTo(channel1);
        assertThat(channels.get(2)).isEqualTo(channel3);
    }
}