package com.levelup.channel.service;

import com.levelup.channel.TestSupporter;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.service.ChannelService;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.constant.ChannelCategory;
import com.levelup.channel.domain.service.dto.CreateChannelDto;
import com.levelup.event.events.ChannelCreatedEvent;
import com.levelup.event.events.EventPublisher;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
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

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    void save() throws IOException {
        // Given
        ChannelMember channelManager1 = createChannelMember(1L, 1L, "manager1", "manager1", true, false);
        Channel channel1 = createChannel(channelManager1, "test channel1", ChannelCategory.STUDY);

        CreateChannelDto channelDto = CreateChannelDto.of(
                1L,
                1L,
                channel1.getName(),
                channelManager1.getNickname(),
                channelManager1.getEmail(),
                channel1.getMemberMaxNumber(),
                channel1.getDescription(),
                channel1.getDescription(),
                0L,
                channel1.getCategory(),
                LocalDate.of(1997, 9, 27),
                LocalDate.of(1997, 9, 27)
        );

        eventPublisher.when((MockedStatic.Verification) EventPublisher.raise(any(ChannelCreatedEvent.class)))
                .thenReturn(ChannelCreatedEvent.of(channelManager1.getMemberId()));

        given(channelRepository.save(any(Channel.class))).willReturn(channelDto.toEntity());

        // When
        ChannelDto newChannelDto = channelService.save(channelDto, channelManager1.getId());

        // Then
        assertThat(newChannelDto.getManagerId()).isEqualTo(1L);
    }
}