package com.levelup.channel.repository;

import com.levelup.channel.ChannelApplicationTest;
import com.levelup.channel.TestSupporter;
import com.levelup.channel.config.TestJpaConfig;
import com.levelup.channel.domain.constant.ChannelCategory;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@ContextConfiguration(classes = {ChannelApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChannelRepositoryTest extends TestSupporter {

    @Autowired ChannelRepository channelRepository;
    @Autowired ChannelMemberRepository channelMemberRepository;

    @BeforeEach
    public void before() {
        channelRepository.deleteAll();
        channelMemberRepository.deleteAll();
    }

    @DisplayName("채널 카테고리별 조회")
    @Test
    void findByCategoryOrderByMemberCount() {
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
        ChannelMember channelMember5 = createChannelMember(8L, "member2", "member2", false, false);
        ChannelMember channelMember6 = createChannelMember(9L, "member2", "member2", false, false);
        channel3.addChannelMember(channelMember6);
        channel1.addChannelMembers(channelMember1, channelMember2);
        channel2.addChannelMembers(channelMember3, channelMember4, channelMember5);
        channelRepository.save(channel1);
        channelRepository.save(channel2);
        channelRepository.save(channel3);

        Pageable pageable = PageRequest.of(0, 10);
        List<Channel> channels1 = channelRepository.findByCategoryOrderByMemberCountDesc(ChannelCategory.STUDY, pageable).getContent();
        assertThat(channels1.get(0)).isEqualTo(channel2);
        assertThat(channels1.get(1)).isEqualTo(channel1);
        assertThat(channels1.get(2)).isEqualTo(channel3);

        List<Channel> channels2 = channelRepository.findByCategoryOrderByIdAsc(ChannelCategory.STUDY, pageable).getContent();
        assertThat(channels2.get(0)).isEqualTo(channel1);
        assertThat(channels2.get(1)).isEqualTo(channel2);
        assertThat(channels2.get(2)).isEqualTo(channel3);
    }
}