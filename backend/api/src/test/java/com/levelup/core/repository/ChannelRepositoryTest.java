package com.levelup.core.repository;

import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.channel.ChannelResponse;
import com.levelup.core.exception.channel.ChannelNotFountExcpetion;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @BeforeEach
    public void setup() {
        memberRepository.deleteAll();
        channelRepository.deleteAll();
    }

    @DisplayName("채널 생성 및 조회 테스트")
    @Test
    void saveChannelAndSelectTest() {
        // Given
        Member member = createMember("testEmail@test.com", "user");
        memberRepository.save(member);

        Channel channel = createChannel(member, "testChannel", ChannelCategory.STUDY);

        // When
        channelRepository.save(channel);
        Channel findChannel = channelRepository.findById(channel.getId())
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        // Then
        assertThat(channel.getName()).isEqualTo(findChannel.getName());
    }

    @DisplayName("카테고리 페이징 조회 테스트")
    @Test
    void findByCategory() {
        // Given
        Member manager = createMember("testEmail@test.com", "testUser");
        memberRepository.save(manager);

        Channel channel1 = createChannel(manager, "testChannel1", ChannelCategory.STUDY);
        Channel channel2 = createChannel(manager, "testChannel2", ChannelCategory.STUDY);
        Channel channel3 = createChannel(manager, "testChannel3", ChannelCategory.PROJECT);
        Channel channel4 = createChannel(manager, "testChannel4", ChannelCategory.PROJECT);
        Channel channel5 = createChannel(manager, "testChannel5", ChannelCategory.PROJECT);
        Channel channel6 = createChannel(manager, "testChannel6", ChannelCategory.PROJECT);
        Channel channel7 = createChannel(manager, "testChannel7", ChannelCategory.PROJECT);
        Channel channel8 = createChannel(manager, "testChannel8", ChannelCategory.PROJECT);
        Channel channel9 = createChannel(manager, "testChannel9", ChannelCategory.PROJECT);
        Channel channel10 = createChannel(manager, "testChannel10", ChannelCategory.PROJECT);
        Channel channel11 = createChannel(manager, "testChannel11", ChannelCategory.PROJECT);
        channelRepository.save(channel1);
        channelRepository.save(channel2);
        channelRepository.save(channel3);
        channelRepository.save(channel4);
        channelRepository.save(channel5);
        channelRepository.save(channel6);
        channelRepository.save(channel7);
        channelRepository.save(channel8);
        channelRepository.save(channel9);
        channelRepository.save(channel10);
        channelRepository.save(channel11);

        // When
        Page<Channel> channelPage = channelRepository.findByCategory(
                ChannelCategory.STUDY, PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"))
        );
        List<ChannelResponse> channelResponses = channelPage.map(ChannelResponse::new).toList();
        for (ChannelResponse channelRespons : channelResponses) {
            System.out.println(channelRespons.getName());
        }
        // Then
        assertThat(channelResponses.size()).isEqualTo(2);
    }
}
