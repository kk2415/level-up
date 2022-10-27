package com.levelup.channel.service;

import com.levelup.channel.TestSupporter;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.service.ChannelArticleService;
import com.levelup.channel.domain.service.dto.ChannelArticleDto;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("채널 게시글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ChannelArticleServiceTest extends TestSupporter {

    @Mock private ChannelArticleRepository mockChannelArticleRepository;

    @InjectMocks private ChannelArticleService channelArticleService;

    @DisplayName("채널 게시글 조회 시 조회수 증가 여부 확인")
    @Test
    void get() {
        // Given
        ChannelMember channelManager1 = createChannelMember(1L, "manager1", "manager1", true, false);
        Channel channel1 = createChannel(channelManager1, "test channel1", ChannelCategory.STUDY);
        ChannelArticle channelArticle1 = createChannelArticle(channelManager1, channel1, "test post1 in channel2");

        // When
        given(mockChannelArticleRepository.findById(anyLong())).willReturn(Optional.of(channelArticle1));
        ChannelArticleDto channelPostDto1 = channelArticleService.get(1L, channel1.getId(), true);

        // Then
        assertThat(channelPostDto1.getViews()).isEqualTo(1L);
    }

    @DisplayName("채널 게시글 수정 테스트")
    @Test
    void update() {
        // Given
        ChannelMember channelManager1 = createChannelMember(1L, 1L, "manager1", "manager1", true, false);
        ChannelMember channelManager2 = createChannelMember(2L, 2L, "manager2", "manager2", true, false);
        Channel channel1 = createChannel(1L, channelManager1, "test channel1", ChannelCategory.STUDY);
        ChannelArticle channelArticle1 = createChannelArticle(channelManager1, channel1, "unchanged title");

        ChannelArticleDto updateDto = ChannelArticleDto.from(createChannelArticle(channelManager1, channel1, "changed title"));

        given(mockChannelArticleRepository.findById(anyLong())).willReturn(Optional.of(channelArticle1));

        // When
        ChannelArticleDto newChannelDto = channelArticleService.update(updateDto, 1L, channelManager1.getMemberId(), channel1.getId());

        // Then
        assertThat(newChannelDto.getTitle()).isEqualTo(updateDto.getTitle());
    }

    @DisplayName("작성자만 수정 가능 여부 테스트")
    @Test
    void updateFail() {
        // Given
        ChannelMember channelManager1 = createChannelMember(1L, 1L, "manager1", "manager1", true, false);
        ChannelMember channelManager2 = createChannelMember(2L, 2L, "manager2", "manager2", true, false);

        Channel channel1 = createChannel(channelManager1, "test channel1", ChannelCategory.STUDY);
        ChannelArticle channelPost1 = createChannelArticle(channelManager1, channel1, "unchanged title");

        ChannelArticleDto updateDto = ChannelArticleDto.from(createChannelArticle(channelManager1, channel1, "changed title"));

        given(mockChannelArticleRepository.findById(anyLong())).willReturn(Optional.of(channelPost1));

        // When & than
        assertThatThrownBy(() -> channelArticleService.update(updateDto, 1L, channelManager2.getId(), channel1.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}