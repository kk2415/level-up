package com.levelup.channel.service;

import com.levelup.channel.TestSupporter;
import com.levelup.channel.domain.service.ChannelMemberService;
import com.levelup.channel.domain.service.dto.ChannelMemberDto;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.member.domain.entity.Member;
import com.levelup.channel.exception.NoPlaceChannelException;
import com.levelup.channel.exception.ChannelMemberDuplicationException;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("채널 멤버 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ChannelMemberServiceTest extends TestSupporter {

    @Mock private MemberRepository mockMemberRepository;
    @Mock private ChannelRepository mockChannelRepository;
    @Mock private ChannelMemberRepository mockChannelMemberRepository;

    @InjectMocks private ChannelMemberService channelMemberService;

    @DisplayName("채널 멤버 추가 테스트")
    @Test
    void create() {
        // Given
        Member member1 = createMember(1L, "manager1", "manager1");
        ChannelMember manager1 = createChannelMember(member1, true, false);
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);

        given(mockMemberRepository.findById(anyLong())).willReturn(Optional.of(member1));
        given(mockChannelRepository.findById(anyLong())).willReturn(Optional.of(channel1));
        given(mockChannelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong())).willReturn(Optional.ofNullable(null));

        // When
        ChannelMemberDto channelMemberDto = channelMemberService.create(1L, manager1.getMember().getId(), true);

        // Then
        assertThat(channelMemberDto.getMemberId()).isEqualTo(manager1.getMember().getId());
        assertThat(channel1.getChannelMembers().size()).isEqualTo(2L);
    }

    @DisplayName("회원 중복 추가 실패 테스트")
    @Test
    void duplicateCreation() {
        // Given
        Member member1 = createMember(1L, "manager1", "manager1");
        ChannelMember manager1 = createChannelMember(member1, true, false);
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);

        given(mockMemberRepository.findById(anyLong())).willReturn(Optional.of(member1));
        given(mockChannelRepository.findById(anyLong())).willReturn(Optional.of(channel1));
        given(mockChannelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong()))
                .willReturn(Optional.ofNullable(createChannelMember(member1, channel1, false)));

        // When && Then
        assertThatThrownBy(() -> channelMemberService.create(1L, manager1.getMember().getId(), true))
                .isInstanceOf(ChannelMemberDuplicationException.class);
    }

    @DisplayName("채널 정원 초과 테스트")
    @Test
    void fullSizeChannel() {
        // Given
        Member member1 = createMember(1L, "manager1", "manager1");
        ChannelMember manager1 = createChannelMember(member1, true, false);
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        List<ChannelMember> channelMembers = List.of(createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false),
                createChannelMember(member1, channel1, false)
        );

        for (ChannelMember channelMember : channelMembers) {
            channel1.addChannelMember(channelMember);
        }

        given(mockMemberRepository.findById(anyLong())).willReturn(Optional.of(member1));
        given(mockChannelRepository.findById(anyLong())).willReturn(Optional.of(channel1));
        given(mockChannelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong()))
                .willReturn(Optional.ofNullable(null));

        // When && Then
        assertThatThrownBy(() -> channelMemberService.create(1L, manager1.getMember().getId(), true))
                .isInstanceOf(NoPlaceChannelException.class);
    }

    @DisplayName("채널 회원 삭제 테스트")
    @Test
    void delete() {
        // Given
        Member member1 = createMember(1L, "manager1", "manager1");
        Member member2 = createMember(2L, "member1", "member1");
        Member member3 = createMember(3L, "member2", "member2");
        Member member4 = createMember(4L, "member3", "member3");
        ChannelMember manager1 = createChannelMember(member1, true, false);
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);

        ChannelMember channelMember2 = createChannelMember(member1, channel1, false);
        ChannelMember channelMember3 = createChannelMember(member2, channel1, false);
        ChannelMember channelMember4 = createChannelMember(member3, channel1, false);

        given(mockChannelMemberRepository.findById(anyLong())).willReturn(Optional.of(channelMember2));
        given(mockChannelRepository.findById(anyLong())).willReturn(Optional.of(channel1));

        // When
        channelMemberService.delete(1L, 1L);

        // Then
        assertThat(channel1.getChannelMembers().size()).isEqualTo(3L);
    }
}