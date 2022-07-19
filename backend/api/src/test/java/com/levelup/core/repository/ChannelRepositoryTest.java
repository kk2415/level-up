package com.levelup.core.repository;

import com.levelup.api.ApiApplication;
import com.levelup.core.domain.auth.EmailAuth;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channel.ChannelMember;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.channel.ChannelRequest;
import com.levelup.core.dto.member.CreateMemberRequest;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("채널 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
public class ChannelRepositoryTest {

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
        Member member = createMember();
        memberRepository.save(member);

        Channel channel = createChannel(member);

        // When
        channelRepository.save(channel);
        Channel findChannel = channelRepository.findById(channel.getId());

        // Then
        assertThat(channel.getName()).isEqualTo(findChannel.getName());
    }

    public Member createMember() {
        CreateMemberRequest memberRequest = CreateMemberRequest.of("test@test.com", "00000000", "test",
                "testNickname", Gender.MALE, "19970927", "010-2354-9960", new UploadFile("", ""));
        EmailAuth authEmail = EmailAuth.createAuthEmail(memberRequest.getEmail());

        Member member = memberRequest.toEntity();
        member.setEmailAuth(authEmail);

        return member;
    }

    public Channel createChannel(Member manager) {
        ChannelRequest channelRequest = ChannelRequest.of("test@test", "testChannel", 5L, "testChannel",
                ChannelCategory.STUDY, "test", new UploadFile("", ""), null);

        Channel channel = channelRequest.toEntity(manager.getNickname());
        ChannelMember channelMember = ChannelMember.createChannelMember(manager, true, false);
        channel.setChannelMember(channelMember);

        return channel;
    }

}
