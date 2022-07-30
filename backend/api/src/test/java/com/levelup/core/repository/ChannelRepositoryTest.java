package com.levelup.core.repository;

import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DisplayName("채널 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
@ExtendWith(SpringExtension.class)
public class ChannelRepositoryTest extends TestSupporter {

    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;

    public ChannelRepositoryTest(@Autowired MemberRepository memberRepository,
                                 @Autowired ChannelRepository channelRepository) {
        this.memberRepository = memberRepository;
        this.channelRepository = channelRepository;
    }

    @DisplayName("채널 생성 및 조회 테스트")
    @Test
    void saveChannelAndSelectTest() {
        // Given
        Member member = createMember("testEmail@test.com", "testUser");
        memberRepository.save(member);

        Channel channel = createChannel(member, "testChannel");

        // When
        channelRepository.save(channel);
        Channel findChannel = channelRepository.findById(channel.getId());

        // Then
        assertThat(channel.getName()).isEqualTo(findChannel.getName());
    }
}
