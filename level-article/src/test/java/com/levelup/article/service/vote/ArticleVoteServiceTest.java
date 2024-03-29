package com.levelup.article.service.vote;

import com.levelup.article.TestSupporter;
import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.service.dto.VoteDto;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.service.vote.ArticleVoteService;
import com.levelup.article.domain.entity.ArticleVote;
import com.levelup.article.domain.repository.ArticleVoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("게시글 추천 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleVoteServiceTest extends TestSupporter {

    @Mock private ArticleVoteRepository mockArticleVoteRepository;
    @InjectMocks private ArticleVoteService articleVoteService;

    @DisplayName("게시글 추천 중복 테스트")
    @Test
    void duplicationArticleVote() {
        // Given
        Writer writer1 = createWriter(1L, 1L, "test", "test");
        Article article = createArticle(1L, writer1, "test article", ArticleType.QNA);
        ArticleVote articleVote1 = createArticleVote(article, writer1.getMemberId());
        ArticleVote articleVote2 = createArticleVote(article, writer1.getMemberId());
        VoteDto voteDto1 = VoteDto.of(articleVote1, true);

        given(mockArticleVoteRepository.findByMemberIdAndArticleId(voteDto1.getMemberId(), voteDto1.getTargetId()))
                .willReturn(List.of(articleVote1));

        // When
        VoteDto newVoteDto = articleVoteService.save(voteDto1);

        // Then
        assertThat(newVoteDto.isSuccessful()).isFalse();
    }
}
