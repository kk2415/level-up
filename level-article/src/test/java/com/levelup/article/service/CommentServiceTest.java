package com.levelup.article.service;

import com.levelup.article.TestSupporter;
import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.repository.WriterRepository;
import com.levelup.article.domain.service.CommentService;
import com.levelup.article.domain.service.dto.ReplyCommentDto;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.Comment;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.CommentRepository;
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
class CommentServiceTest extends TestSupporter {

    @Mock private ArticleRepository mockArticleRepository;
    @Mock private CommentRepository mockCommentRepository;
    @Mock private WriterRepository mockWriterRepository;

    @InjectMocks private CommentService commentService;

    @DisplayName("대댓글 추가 테스트")
    @Test
    void saveReply() {
        // Given
        Writer writer1 = createWriter(1L, 1L, "test", "test");
        Article article1 = createArticle(writer1, "test article1", ArticleType.NOTICE);
        Comment comment1 = createComment(writer1, article1);
        ReplyCommentDto replyCommentDto = ReplyCommentDto.of(1L, 1L, "test", ArticleType.NOTICE);

        given(mockWriterRepository.findByMemberId(anyLong())).willReturn(Optional.of(writer1));
        given(mockArticleRepository.findById(anyLong())).willReturn(Optional.of(article1));
        given(mockCommentRepository.findById(anyLong())).willReturn(Optional.of(comment1));

        // When
        commentService.saveReply(replyCommentDto, writer1.getMemberId());

        // Then
        assertThat(comment1.getChild().size()).isEqualTo(1L);
    }
}