package com.levelup.api.service;

import com.levelup.TestSupporter;
import com.levelup.api.dto.service.channelPost.ChannelPostDto;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.ChannelPost.ChannelPostRepository;
import org.assertj.core.api.Assertions;
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

@ExtendWith(MockitoExtension.class)
class ChannelPostServiceTest extends TestSupporter {

    @Mock private ChannelPostRepository mockChannelPostRepository;
    @InjectMocks private ChannelPostService channelPostService;

    @DisplayName("채널 게시글 조회 시 조회수 증가 여부 확인")
    @Test
    void get() {
        // Given
        Member manager1 = createMember("manager1", "manager1");
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        ChannelPost channelPost1 = createChannelPost(manager1, channel1, "test post1 in channel2");

        // When
        given(mockChannelPostRepository.findByArticleId(anyLong())).willReturn(Optional.of(channelPost1));
        ChannelPostDto channelPostDto1 = channelPostService.get(1L, true);

        // Then
        assertThat(channelPostDto1.getViews()).isEqualTo(1L);
    }

    @DisplayName("채널 게시글 수정 테스트")
    @Test
    void update() {
        // Given
        Member manager1 = createMember(1L, "manager1", "manager1");
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        ChannelPost channelPost1 = createChannelPost(manager1, channel1, "unchanged title");

        ChannelPostDto updateDto = ChannelPostDto.from(createChannelPost(manager1, channel1, "changed title"));

        given(mockChannelPostRepository.findByArticleId(anyLong())).willReturn(Optional.of(channelPost1));

        // When
        ChannelPostDto newChannelDto = channelPostService.update(updateDto, 1L, manager1.getId());

        // Then
        assertThat(newChannelDto.getTitle()).isEqualTo(updateDto.getTitle());
    }

    @DisplayName("작성자만 수정 가능 여부 테스트")
    @Test
    void updateFail() {
        // Given
        Member manager1 = createMember(1L, "manager1", "manager1");
        Member manager2 = createMember(2L, "manager1", "manager1");
        Channel channel1 = createChannel(manager1, "test channel1", ChannelCategory.STUDY);
        ChannelPost channelPost1 = createChannelPost(manager1, channel1, "unchanged title");

        ChannelPostDto updateDto = ChannelPostDto.from(createChannelPost(manager1, channel1, "changed title"));

        given(mockChannelPostRepository.findByArticleId(anyLong())).willReturn(Optional.of(channelPost1));

        // When & than
        assertThatThrownBy(() -> channelPostService.update(updateDto, 1L, manager2.getId()))
                .isInstanceOf(MemberNotFoundException.class);
    }
}