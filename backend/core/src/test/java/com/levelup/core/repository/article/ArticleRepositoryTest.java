package com.levelup.core.repository.article;

import com.levelup.core.TestSupporter;
import com.levelup.core.config.TestJpaConfig;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.ArticlePagingDto;
import com.levelup.core.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest extends TestSupporter {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired MemberRepository memberRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void findByArticleType() {
        Member member1 = createMember("test", "test1");
        Article article1 = createArticle(member1, "test article1", ArticleType.QNA);
        Article article2 = createArticle(member1, "test article2", ArticleType.QNA);
        Article article3 = createArticle(member1, "test article3", ArticleType.NOTICE);

        memberRepository.save(member1);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        Page<ArticlePagingDto> articles
                = articleRepository.findByArticleType(ArticleType.QNA.toString(), PageRequest.of(0, 10));

        assertThat(articles.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findByTitleAndArticleType() {
        Member member1 = createMember("member1", "member1");
        Member member2 = createMember("member2", "member2");
        Member member3 = createMember("member3", "member3");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Article article1 = createArticle(member1, "test", ArticleType.QNA);
        Article article2 = createArticle(member2, "test", ArticleType.QNA);
        Article article3 = createArticle(member3, "test", ArticleType.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        Page<ArticlePagingDto> articles
                = articleRepository.findByTitleAndArticleType("test", ArticleType.QNA.name(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "article_id")));
    }

    @Test
    void findByNicknameAndArticleType() {
        Member member1 = createMember("member1", "member1");
        Member member2 = createMember("member2", "member2");
        Member member3 = createMember("member3", "member3");
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Article article1 = createArticle(member1, "test", ArticleType.QNA);
        Article article2 = createArticle(member2, "test", ArticleType.QNA);
        Article article3 = createArticle(member3, "test", ArticleType.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        Page<ArticlePagingDto> articles
                = articleRepository.findByNicknameAndArticleType("member2", ArticleType.QNA.name(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "article_id")));

        assertThat(articles.getTotalElements()).isEqualTo(1);
        for (ArticlePagingDto article : articles) {
            assertThat(article.getWriter()).isEqualTo(member2.getNickname());
        }
    }

    @Test
    void findNextByIdAndArticleType() {
        Member member1 = createMember("member1", "member1");
        memberRepository.save(member1);

        Article article1 = createArticle(member1, "test1", ArticleType.QNA);
        Article article2 = createArticle(member1, "test2", ArticleType.QNA);
        Article article3 = createArticle(member1, "test3", ArticleType.NOTICE);
        Article article4 = createArticle(member1, "test4", ArticleType.QNA);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        Article article = articleRepository.findNextByIdAndArticleType(article2.getId(), ArticleType.QNA.name())
                .orElseThrow();

        assertThat(article.getId()).isEqualTo(article4.getId());
        assertThat(article.getTitle()).isEqualTo(article4.getTitle());
    }

    @Test
    void findPrevByIdAndArticleType() {
        Member member1 = createMember("member1", "member1");
        memberRepository.save(member1);

        Article article1 = createArticle(member1, "test1", ArticleType.QNA);
        Article article2 = createArticle(member1, "test2", ArticleType.QNA);
        Article article3 = createArticle(member1, "test3", ArticleType.NOTICE);
        Article article4 = createArticle(member1, "test4", ArticleType.QNA);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        Article article = articleRepository.findPrevByIdAndArticleType(article2.getId(), ArticleType.QNA.name())
                .orElseThrow();

        assertThat(article.getId()).isEqualTo(article1.getId());
        assertThat(article.getTitle()).isEqualTo(article1.getTitle());
    }
}