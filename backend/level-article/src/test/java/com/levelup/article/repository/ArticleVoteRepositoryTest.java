package com.levelup.article.repository;

import com.levelup.article.ArticleApplicationTest;
import com.levelup.article.TestSupporter;
import com.levelup.article.config.TestJpaConfig;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.entity.ArticleVote;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.ArticleVoteRepository;
import com.levelup.member.MemberApplication;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.repository.MemberRepository;
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
@ContextConfiguration(classes = {ArticleApplicationTest.class, MemberApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleVoteRepositoryTest extends TestSupporter {

    @Autowired MemberRepository memberRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleVoteRepository articleVoteRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        memberRepository.deleteAll();
        articleVoteRepository.deleteAll();
    }

    @DisplayName("게시글 추천 조회")
    @Test
    void findByMemberIdAndArticleId() {
        // Given
        Member member1 = createMember("test1", "test1");
        Member member2 = createMember("test2", "test2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Article article1 = createArticle(member1, "test article1", ArticleType.QNA);
        Article article2 = createArticle(member1, "test article2", ArticleType.QNA);
        articleRepository.save(article1);
        articleRepository.save(article2);

        ArticleVote articleVote1 = createArticleVote(article1, member1.getId());
        ArticleVote articleVote2 = createArticleVote(article1, member2.getId());
        ArticleVote articleVote3 = createArticleVote(article2, member1.getId());
        articleVoteRepository.save(articleVote1);
        articleVoteRepository.save(articleVote2);
        articleVoteRepository.save(articleVote3);

        // When
        List<ArticleVote> articleVotes
                = articleVoteRepository.findByMemberIdAndArticleId(member1.getId(), article1.getId());

        // Then
        assertThat(articleVotes.get(0).getMemberId()).isEqualTo(member1.getId());
        assertThat(articleVotes.get(0).getArticle()).isEqualTo(article1);
    }
}