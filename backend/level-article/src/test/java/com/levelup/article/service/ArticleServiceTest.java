package com.levelup.article.service;

import com.levelup.article.TestSupporter;
import com.levelup.article.domain.service.ArticleService;
import com.levelup.article.exception.ArticleAuthorityException;
import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.ArticleType;
import com.levelup.member.domain.entity.Member;
import com.levelup.article.domain.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("게시글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest extends TestSupporter {

    @Mock private ArticleRepository mockArticleRepository;

    @InjectMocks private ArticleService articleService;

    @DisplayName("아티클 조회 시 조회수 증가 여부 확인")
    @Test
    void get() {
        // Given
        Member member = createMember("test", "test");
        Article article = createArticle(member, "test article", ArticleType.QNA);
        given(mockArticleRepository.findById(article.getId())).willReturn(Optional.of(article));

        // When
        ArticleDto articleDto = articleService.get(article.getId(), article.getArticleType(), true);

        // Then
        assertThat(articleDto.getViews()).isEqualTo(1L);
    }

    @DisplayName("아티클 수정 테스트")
    @Test
    void update() {
        // Given
        Member member = createMember(1L, "test", "test");
        Article article = createArticle(member, "test article", ArticleType.QNA);
        given(mockArticleRepository.findById(anyLong())).willReturn(Optional.of(article));

        // When
        ArticleDto newArticleDto = articleService.update(
                ArticleDto.of(
                        null,
                        null,
                        "changed title",
                        null,
                        "changed content",
                        null,
                        null,
                        null,
                        null,
                        null), 1L, member.getId());

        // Then
        assertThat(newArticleDto.getTitle()).isEqualTo("changed title");
        assertThat(newArticleDto.getContent()).isEqualTo("changed content");
    }

    @DisplayName("작성자만 수정 가능 여부 테스트")
    @Test
    void updateFail() {
        // Given
        Member member1 = createMember(1L, "test1", "test1");
        Member member2 = createMember(2L, "test2", "test2");
        Article article = createArticle(member1, "unchanged article", ArticleType.QNA);
        given(mockArticleRepository.findById(anyLong())).willReturn(Optional.of(article));

        // When & Then
        assertThatThrownBy(
                () -> articleService.update(
                        ArticleDto.of(
                                null,
                                null,
                                "changed title",
                                null,
                                "changed content",
                                null,
                                null,
                                null,
                                null,
                                null), 1L, member2.getId()))
                .isInstanceOf(ArticleAuthorityException.class);
    }
}