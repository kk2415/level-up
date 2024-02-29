package com.levelup.article.repository;

import com.levelup.article.ArticleApplicationTest;
import com.levelup.article.TestSupporter;
import com.levelup.article.config.TestJpaConfig;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.ArticleVote;
import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.ArticleVoteRepository;
import com.levelup.article.domain.repository.WriterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 추천 레포지토리 테스트")
@ActiveProfiles("test")
@Transactional
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ArticleApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleVoteRepositoryTest extends TestSupporter {

    @Autowired WriterRepository writerRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired ArticleVoteRepository articleVoteRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        articleVoteRepository.deleteAll();
    }

    @DisplayName("게시글 추천 조회")
    @Test
    void findByMemberIdAndArticleId() {
        // Given
        Writer writer1 = createWriter(1L, "test1", "test1");
        Writer writer2 = createWriter(2L, "test2", "test2");
        writerRepository.save(writer1);
        writerRepository.save(writer2);

        Article article1 = createArticle(writer1, "test article1", ArticleType.QNA);
        Article article2 = createArticle(writer2, "test article2", ArticleType.QNA);
        articleRepository.save(article1);
        articleRepository.save(article2);

        ArticleVote articleVote1 = createArticleVote(article1, writer1.getMemberId());
        ArticleVote articleVote2 = createArticleVote(article1, writer1.getMemberId());
        ArticleVote articleVote3 = createArticleVote(article2, writer2.getMemberId());
        articleVoteRepository.save(articleVote1);
        articleVoteRepository.save(articleVote2);
        articleVoteRepository.save(articleVote3);

        // When
        List<ArticleVote> articleVotes
                = articleVoteRepository.findByMemberIdAndArticleId(writer1.getMemberId(), article1.getId());

        // Then
        assertThat(articleVotes.get(0).getMemberId()).isEqualTo(writer1.getMemberId());
        assertThat(articleVotes.get(0).getArticle()).isEqualTo(article1);
    }
}