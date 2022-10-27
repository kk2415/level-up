package com.levelup.channel.repository;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("채널 멤버 레포지토리 테스트")
@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ChannelApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelMemberRepositoryTest extends TestSupporter {

    @Autowired ChannelRepository channelRepository;
    @Autowired ChannelMemberRepository channelMemberRepository;

    @BeforeEach
    public void before() {
        channelRepository.deleteAll();
        channelMemberRepository.deleteAll();
    }

    @DisplayName("채널 멤버 조회")
    @Test
    void findByChannelIdAndIsWaitingMember() {
        // Given
        ChannelMember channelManager1 = createChannelMember(1L, "manager1", "manager1", true, false);
        ChannelMember channelManager2 = createChannelMember(2L, "manager2", "manager2", true, false);
        ChannelMember channelManager3 = createChannelMember(3L, "manager3", "manager3", true, false);
        Channel channel1 = createChannel(channelManager1, "test channel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(channelManager2, "test channel2", ChannelCategory.STUDY);
        Channel channel3 = createChannel(channelManager3, "test channel3", ChannelCategory.STUDY);

        ChannelMember channelMember1 = createChannelMember(4L, "member1", "member1", false, false);
        ChannelMember channelMember2 = createChannelMember(5L, "member2", "member2", false, false);
        ChannelMember channelMember3 = createChannelMember(6L, "member2", "member2", false, false);
        ChannelMember channelMember4 = createChannelMember(7L, "member2", "member2", false, false);
        ChannelMember channelMember5 = createChannelMember(8L, "member2", "member2", false, true);
        ChannelMember channelMember6 = createChannelMember(9L, "member2", "member2", false, true);
        channel1.addChannelMembers(channelMember1, channelMember2);
        channel2.addChannelMembers(channelMember3, channelMember4, channelMember5, channelMember6);
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
        assertThat(waitingChannelMembers.getTotalElements()).isEqualTo(2);
    }
}