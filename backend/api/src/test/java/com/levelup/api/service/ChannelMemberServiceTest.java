package com.levelup.api.service;

import com.levelup.TestSupporter;
import com.levelup.api.dto.service.chanelMember.ChannelMemberDto;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.channel.NoPlaceChannelException;
import com.levelup.core.exception.channelMember.DuplicateChannelMemberException;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.channelMember.ChannelMemberRepository;
import com.levelup.core.repository.member.MemberRepository;
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

@ExtendWith(MockitoExtension.class)
class ChannelMemberServiceTest extends TestSupporter {

    @Mock private ChannelRepository mockChannelRepository;
    @Mock private MemberRepository mockMemberRepository;
    @Mock private ChannelMemberRepository mockChannelMemberRepository;
    @InjectMocks private ChannelMemberService channelMemberService;

    @DisplayName("채널 멤버 추가 테스트")
    @Test
    void create() {
        // Given
        Member manager1 = createMember(1L, "manager1", "manager1");
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);

        given(mockMemberRepository.findById(anyLong())).willReturn(Optional.of(manager1));
        given(mockChannelRepository.findById(anyLong())).willReturn(Optional.of(channel1));
        given(mockChannelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong())).willReturn(List.of());

        // When
        ChannelMemberDto channelMemberDto = channelMemberService.create(1L, manager1.getId(), true);

        // Then
        assertThat(channelMemberDto.getMemberId()).isEqualTo(manager1.getId());
        assertThat(channel1.getChannelMembers().size()).isEqualTo(2L);
    }

    @DisplayName("회원 중복 추가 실패 테스트")
    @Test
    void duplicateCreation() {
        // Given
        Member manager1 = createMember(1L, "manager1", "manager1");
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);

        given(mockMemberRepository.findById(anyLong())).willReturn(Optional.of(manager1));
        given(mockChannelRepository.findById(anyLong())).willReturn(Optional.of(channel1));
        given(mockChannelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong()))
                .willReturn(List.of(createChannelMember(manager1, channel1, false)));

        // When && Then
        assertThatThrownBy(() -> channelMemberService.create(1L, manager1.getId(), true))
                .isInstanceOf(DuplicateChannelMemberException.class);
    }

    @DisplayName("채널 정원 초과 테스트")
    @Test
    void fullSizeChannel() {
        // Given
        Member manager1 = createMember(1L, "manager1", "manager1");
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        List<ChannelMember> channelMembers = List.of(createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false),
                createChannelMember(manager1, channel1, false)
        );

        for (ChannelMember channelMember : channelMembers) {
            channel1.setChannelMember(channelMember);
        }

        given(mockMemberRepository.findById(anyLong())).willReturn(Optional.of(manager1));
        given(mockChannelRepository.findById(anyLong())).willReturn(Optional.of(channel1));
        given(mockChannelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong()))
                .willReturn(List.of());

        // When && Then
        assertThatThrownBy(() -> channelMemberService.create(1L, manager1.getId(), true))
                .isInstanceOf(NoPlaceChannelException.class);
    }

    @Test
    void delete() {
        // Given
        Member manager1 = createMember(1L, "manager1", "manager1");
        Member member1 = createMember(2L, "member1", "member1");
        Member member2 = createMember(3L, "member2", "member2");
        Member member3 = createMember(4L, "member3", "member3");
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