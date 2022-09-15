package com.levelup.api.service.vote;

import com.levelup.TestSupporter;
import com.levelup.api.service.dto.vote.VoteDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.vote.ArticleVoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleVoteServiceTest extends TestSupporter {

    @Mock private ArticleRepository mockArticleRepository;
    @Mock private ArticleVoteRepository mockArticleVoteRepository;
    @InjectMocks private ArticleVoteService articleVoteService;

    @DisplayName("게시글 추천 중복 테스트")
    @Test
    void duplicationArticleVote() {
        // Given
        Member member = createMember(1L, "test", "test");
        Article article = createArticle(1L, member, "test article", ArticleType.QNA);
        ArticleVote articleVote1 = createArticleVote(article, member.getId());
        ArticleVote articleVote2 = createArticleVote(article, member.getId());
        VoteDto voteDto1 = VoteDto.of(articleVote1, true);


        given(mockArticleVoteRepository.findByMemberIdAndArticleId(voteDto1.getMemberId(), voteDto1.getTargetId()))
                .willReturn(List.of(articleVote1));

        // When
        VoteDto newVoteDto = articleVoteService.save(voteDto1);

        // Then
        assertThat(newVoteDto.isSuccessful()).isFalse();
    }
}