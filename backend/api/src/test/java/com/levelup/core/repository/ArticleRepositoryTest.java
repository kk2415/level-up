package com.levelup.core.repository;

import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DisplayName("채널 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
@ExtendWith(SpringExtension.class)
public class ArticleRepositoryTest extends TestSupporter {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public ArticleRepositoryTest(@Autowired MemberRepository memberRepository, @Autowired ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    @AfterEach
    public void setup() {
        memberRepository.deleteAll();
        articleRepository.deleteAll();
    }

    @DisplayName("아티클 페이징 조회")
    @Test
    void findByArticleTypeAndTitle() {
        Member member1 = createMember("test1@test.com", "test1");
        memberRepository.save(member1);

        Article article1 = createArticle(member1, "test article1", ArticleType.NOTICE);
        Article article2 = createArticle(member1, "test article2", ArticleType.NOTICE);
        Article article3 = createArticle(member1, "test article3", ArticleType.NOTICE);
        Article article4 = createArticle(member1, "test article4", ArticleType.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        Page<Article> articlePage = articleRepository.findByArticleTypeAndTitle(ArticleType.NOTICE, "test",
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));

        Assertions.assertThat(articlePage.getTotalElements()).isEqualTo(4);
    }
}
