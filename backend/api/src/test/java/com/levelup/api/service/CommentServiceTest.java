package com.levelup.api.service;

import com.levelup.TestSupporter;
import com.levelup.api.dto.service.comment.ReplyCommentDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest extends TestSupporter {

    @Mock private MemberRepository mockMemberRepository;
    @Mock private ArticleRepository mockArticleRepository;
    @Mock private CommentRepository mockCommentRepository;
    @InjectMocks private CommentService commentService;

    @DisplayName("대댓글 추가 테스트")
    @Test
    void saveReply() {
        // Given
        Member member1 = createMember(1L, "test1", "test1");
        Article article1 = createArticle(member1, "test article1", ArticleType.NOTICE);
        Comment comment1 = createComment(member1, article1);
        ReplyCommentDto replyCommentDto = ReplyCommentDto.of(1L, 1L, "test", ArticleType.NOTICE);

        given(mockMemberRepository.findById(anyLong())).willReturn(Optional.of(member1));
        given(mockArticleRepository.findById(anyLong())).willReturn(Optional.of(article1));
        given(mockCommentRepository.findById(anyLong())).willReturn(Optional.of(comment1));

        // When
        commentService.saveReply(replyCommentDto, member1.getId());

        // Then
        assertThat(comment1.getChild().size()).isEqualTo(1L);
    }
}