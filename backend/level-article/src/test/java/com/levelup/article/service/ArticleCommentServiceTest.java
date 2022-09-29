package com.levelup.article.service;

import com.levelup.article.TestSupporter;
import com.levelup.article.domain.service.CommentService;
import com.levelup.article.domain.service.dto.ReplyCommentDto;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.entity.ArticleComment;
import com.levelup.member.domain.entity.Member;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.CommentRepository;
import com.levelup.member.domain.repository.MemberRepository;
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

@DisplayName("게시글 댓글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest extends TestSupporter {

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
        ArticleComment comment1 = createComment(member1, article1);
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