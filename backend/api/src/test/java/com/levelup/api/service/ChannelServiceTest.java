package com.levelup.api.service;

import com.levelup.TestSupporter;
import com.levelup.api.service.dto.channel.ChannelDto;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ChannelServiceTest extends TestSupporter {

    @Mock private ChannelRepository channelRepository;
    @Mock private MemberRepository memberRepository;
    @InjectMocks private ChannelService channelService;

    @DisplayName("채널 생성시 유저에 매니저 권한 부여 and 생성된 채널에 유저가 자동 등록됨")
    @Test
    void save() {
        // Given
        Member manager1 = createMember(1L, "manager1", "manager1");
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        ChannelDto channelDto = ChannelDto.builder()
                .channelId(1L)
                .managerId(manager1.getId())
                .name(channel1.getName())
                .managerNickname(manager1.getNickname())
                .description(channel1.getDescription())
                .limitedMemberNumber(channel1.getMemberMaxNumber())
                .thumbnailImage(channel1.getThumbnailImage())
                .category(channel1.getCategory())
                .expectedStartDate(LocalDate.of(1997, 9, 27))
                .expectedEndDate(LocalDate.of(1997, 9, 27))
                .build();

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(manager1));
        given(channelRepository.save(any(Channel.class))).willReturn(channelDto.toEntity(manager1.getNickname()));

        // When
        ChannelDto newChannelDto = channelService.save(channelDto, manager1.getId());

        // Then
        assertThat(newChannelDto.getMemberCount()).isEqualTo(1L);
        assertThat(manager1.getRoles().size()).isEqualTo(2L);
        assertThat(manager1.getRoles().get(manager1.getRoles().size() - 1).getRoleName()).isEqualTo(RoleName.CHANNEL_MANAGER);
    }
}