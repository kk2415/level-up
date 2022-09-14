package com.levelup.core.repository.vote;

import com.levelup.core.TestSupporter;
import com.levelup.core.config.TestJpaConfig;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleVoteRepositoryTest extends TestSupporter {

    @Autowired MemberRepository memberRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired ArticleVoteRepository articleVoteRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        memberRepository.deleteAll();
        articleVoteRepository.deleteAll();
    }

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