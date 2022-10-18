package com.levelup.channel.service;

import com.levelup.channel.TestSupporter;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelComment;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.comment.ChannelCommentRepository;
import com.levelup.channel.domain.service.ChannelCommentService;
import com.levelup.channel.domain.service.dto.ChannelCommentDto;
import com.levelup.member.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("채널 댓글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ChannelCommentServiceTest extends TestSupporter {

    @Mock private ChannelCommentRepository commentRepository;
    @Mock private ChannelArticleRepository articleRepository;
    @Mock private ChannelMemberRepository channelMemberRepository;

    @InjectMocks private ChannelCommentService channelCommentService;

    @DisplayName("채널 댓글 작성")
    @Test
    void save() {
        // Given
        Member member = createMember(1L, "test", "test");
        ChannelMember channelMember = createChannelMember(1L, member, true, false);
        Channel channel = createChannel(1L, channelMember, "test channel", ChannelCategory.STUDY);
        ChannelArticle article = createChannelArticle(1L, channelMember, channel, "test article");

        ChannelCommentDto dto = ChannelCommentDto.of(
                null,
                1L,
                null,
                "test",
                LocalDateTime.now(),
                0L,
                0L);

        given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));
        given(channelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong()))
                .willReturn(Optional.of(channelMember));

        // When
        ChannelCommentDto newDto = channelCommentService.save(dto, 1L, 1L, 1L);

        // Then
        assertThat(newDto.getNickname()).isEqualTo(member.getNickname());
    }

    @DisplayName("채널 대댓글 작성")
    @Test
    void saveReply() {
        // Given
        Member member = createMember(1L, "test", "test");
        ChannelMember channelMember = createChannelMember(1L, member, true, false);
        Channel channel = createChannel(1L, channelMember, "test channel", ChannelCategory.STUDY);
        ChannelArticle article = createChannelArticle(1L, channelMember, channel, "test article");
        ChannelComment parent = createChannelComment(1L, channelMember, article);

        ChannelCommentDto child = ChannelCommentDto.of(
                null,
                1L,
                null,
                "reply comment",
                LocalDateTime.now(),
                0L,
                0L);

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(parent));
        given(channelMemberRepository.findByChannelIdAndMemberId(anyLong(), anyLong()))
                .willReturn(Optional.of(channelMember));

        // When
        ChannelCommentDto newDto = channelCommentService.saveReply(child, 1L, 1L);

        // Then
        assertThat(newDto.getNickname()).isEqualTo(member.getNickname());
    }

    @DisplayName("채널 댓글 조회")
    @Test
    void getComments() {
        // Given
        Member member = createMember(1L, "test", "test");
        ChannelMember channelMember = createChannelMember(1L, member, true, false);
        Channel channel = createChannel(1L, channelMember, "test channel", ChannelCategory.STUDY);
        ChannelArticle article = createChannelArticle(1L, channelMember, channel, "test article");
        ChannelComment comment1 = createChannelComment(1L, channelMember, article);
        ChannelComment comment2 = createChannelComment(2L, channelMember, article);
        ChannelComment comment3 = createChannelComment(3L, channelMember, article);

        given(commentRepository.findByArticleId(anyLong()))
                .willReturn(List.of(comment1, comment2, comment3));

        // When
        List<ChannelCommentDto> comments = channelCommentService.getComments(article.getId());

        // Then
        assertThat(comments.size()).isEqualTo(3);
        assertThat(comments.get(0).getNickname()).isEqualTo(member.getNickname());
        assertThat(comments.get(1).getNickname()).isEqualTo(member.getNickname());
        assertThat(comments.get(2).getNickname()).isEqualTo(member.getNickname());
    }

    @DisplayName("채널 댓글 수정")
    @Test
    void update() {
        // Given
        final String updateContent = "updated content";

        Member member = createMember(1L, "test", "test");
        ChannelMember channelMember = createChannelMember(1L, member, true, false);
        Channel channel = createChannel(1L, channelMember, "test channel", ChannelCategory.STUDY);
        ChannelArticle article = createChannelArticle(1L, channelMember, channel, "test article");
        ChannelComment comment = createChannelComment(1L, channelMember, article);

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        // When
        channelCommentService.update(comment.getId(), updateContent);

        // Then
        assertThat(comment.getContent()).isEqualTo(updateContent);
    }
}