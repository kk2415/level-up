package com.levelup.channel.service;

import com.levelup.channel.TestSupporter;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.service.ChannelService;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.event.events.ChannelCreatedEvent;
import com.levelup.event.events.EventPublisher;
import com.levelup.event.events.MemberCreatedEvent;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("채널 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ChannelServiceTest extends TestSupporter {

    @Mock private ChannelRepository channelRepository;

    @InjectMocks private ChannelService channelService;

    private MockedStatic<EventPublisher> eventPublisher;

    @BeforeEach
    public void before() {
        eventPublisher = Mockito.mockStatic(EventPublisher.class);
    }

    @AfterEach
    public void after() {
        eventPublisher.close();
    }

    @DisplayName("채널 생성시 유저에 매니저 권한 부여 and 생성된 채널에 유저가 자동 등록됨")
    @Test
    void save() {
        // Given
        Member member1 = createMember(1L, "manager1", "manager1");
        ChannelMember manager1 = createChannelMember(
                member1.getId(),
                member1.getEmail(),
                member1.getNickname(),
                member1.getProfileImage().getStoreFileName(),
                true,
                false);
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);

        ChannelDto channelDto = ChannelDto.builder()
                .channelId(1L)
                .managerId(manager1.getId())
                .name(channel1.getName())
                .managerNickname(member1.getNickname())
                .description(channel1.getDescription())
                .limitedMemberNumber(channel1.getMemberMaxNumber())
                .thumbnailImage(channel1.getThumbnail())
                .category(channel1.getCategory())
                .expectedStartDate(LocalDate.of(1997, 9, 27))
                .expectedEndDate(LocalDate.of(1997, 9, 27))
                .build();

        eventPublisher.when((MockedStatic.Verification) EventPublisher.raise(any(ChannelCreatedEvent.class)))
                .thenReturn(ChannelCreatedEvent.of(
                        channelDto.getChannelId(),
                        manager1.getMemberId(),
                        manager1.getEmail(),
                        manager1.getNickname(),
                        manager1.getProfileImage()));

        given(channelRepository.save(any(Channel.class))).willReturn(channelDto.toEntity(member1.getNickname()));

        // When
        ChannelDto newChannelDto = channelService.save(
                channelDto,
                member1.getId(),
                member1.getEmail(),
                member1.getNickname(),
                member1.getProfileImage().getStoreFileName());

        // Then
        assertThat(newChannelDto.getManagerId()).isEqualTo(manager1.getId());
    }
}