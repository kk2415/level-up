package com.levelup.core.repository.channelMember;

import com.levelup.core.TestSupporter;
import com.levelup.core.config.TestJpaConfig;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelMemberRepositoryTest extends TestSupporter {

    @Autowired MemberRepository memberRepository;
    @Autowired ChannelRepository channelRepository;
    @Autowired ChannelMemberRepository channelMemberRepository;

    @BeforeEach
    public void before() {
        channelRepository.deleteAll();
        memberRepository.deleteAll();
        channelMemberRepository.deleteAll();
    }

    @Test
    void findByChannelIdAndIsWaitingMember() {
        // Given
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

        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(manager2, "test channel2", ChannelCategory.STUDY);
        Channel channel3 = createChannel(manager3, "test channel3", ChannelCategory.STUDY);
        ChannelMember channelMember1 = createChannelMember(member1, channel1, false);
        ChannelMember channelMember2 = createChannelMember(member2, channel1, false);
        ChannelMember channelMember3 = createChannelMember(member3, channel2, false);
        ChannelMember channelMember4 = createChannelMember(member4, channel2, true);
        ChannelMember channelMember5 = createChannelMember(member5, channel2, false);
        ChannelMember channelMember6 = createChannelMember(member5, channel3, false);
        channelRepository.save(channel1);
        channelRepository.save(channel2);
        channelRepository.save(channel3);

        // When
        Page<ChannelMember> channelMembers
                = channelMemberRepository.findByChannelIdAndIsWaitingMember(
                        channel2.getId(), false, PageRequest.of(0, 10));

        Page<ChannelMember> waitingChannelMembers
                = channelMemberRepository.findByChannelIdAndIsWaitingMember(
                channel2.getId(), true, PageRequest.of(0, 10));

        // Then
        assertThat(channelMembers.getTotalElements()).isEqualTo(3);
        assertThat(waitingChannelMembers.getTotalElements()).isEqualTo(1);
    }
}