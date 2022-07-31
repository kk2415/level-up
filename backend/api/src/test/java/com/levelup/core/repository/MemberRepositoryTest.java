package com.levelup.core.repository;

import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channel.ChannelMember;
import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("멤버 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
public class MemberRepositoryTest extends TestSupporter {

    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;

    @BeforeEach
    public void setup() {
        memberRepository.deleteAll();
        channelRepository.deleteAll();
    }

    public MemberRepositoryTest(@Autowired MemberRepository memberRepository,
                                @Autowired ChannelRepository channelRepository) {
        this.memberRepository = memberRepository;
        this.channelRepository = channelRepository;
    }

    @DisplayName("멤버 생성 및 조회 테스트")
    @Test
    void saveMemberAndSelectTest() {
        // Given
        Member member = createMember("testEmail@test.com", "testUser");

        memberRepository.save(member);

        // When
        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));

        // Then
        assertThat(findMember).isNotNull();
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
    }

    @DisplayName("채널 가입된 멤버 조회 테스트")
    @Test
    void findByChannelId() {
        // Given
        Member manager = createMember("testEmail@test.com", "testUser");
        Member member1 = createMember("testEmail@test.com", "testUser");
        Member member2 = createMember("testEmail@test.com", "testUser");
        memberRepository.save(manager);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Channel channel = createChannel(manager, "testChannel", ChannelCategory.STUDY);
        channelRepository.save(channel);

        ChannelMember channelMember1 = ChannelMember.createChannelMember(member1, false, false);
        ChannelMember channelMember2 = ChannelMember.createChannelMember(member2, false, false);
        channel.setChannelMember(channelMember1);
        channel.setChannelMember(channelMember2);

        // When
        List<Member> findMembers = memberRepository.findByChannelId(channel.getId());

        // Then
        assertThat(findMembers.size()).isEqualTo(3);
    }
}
