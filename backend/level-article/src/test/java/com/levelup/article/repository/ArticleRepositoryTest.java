package com.levelup.article.repository;

import com.levelup.article.ArticleApplicationTest;
import com.levelup.article.TestSupporter;
import com.levelup.article.config.TestJpaConfig;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.WriterRepository;
import com.levelup.article.domain.service.dto.ArticleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("게시글 레포지토리 테스트")
@ActiveProfiles("test")
@Transactional
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ArticleApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest extends TestSupporter {

    @Autowired ArticleRepository articleRepository;
    @Autowired WriterRepository writerRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        writerRepository.deleteAll();
    }

    @DisplayName("타입으로 게시글 조회")
    @Test
    void findByArticleType() {
        Writer writer = createWriter(1L, "test", "test1");
        Article article1 = createArticle(writer, "test article1", ArticleType.QNA);
        Article article2 = createArticle(writer, "test article2", ArticleType.QNA);
        Article article3 = createArticle(writer, "test article3", ArticleType.NOTICE);

        writerRepository.save(writer);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        Page<ArticleDto> articles
                = articleRepository.findByArticleType(ArticleType.QNA, PageRequest.of(0, 10));

        assertThat(articles.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("제목과 타입으로 게시글 조회")
    @Test
    void findByTitleAndArticleType() {
        Writer writer1 = createWriter(1L, "member1", "member1");
        Writer writer2 = createWriter(2L, "member2", "member2");
        Writer writer3 = createWriter(3L, "member3", "member3");
        writerRepository.save(writer1);
        writerRepository.save(writer2);
        writerRepository.save(writer3);

        Article article1 = createArticle(writer1, "test", ArticleType.QNA);
        Article article2 = createArticle(writer2, "test", ArticleType.QNA);
        Article article3 = createArticle(writer3, "test", ArticleType.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        Page<ArticleDto> articles
                = articleRepository.findByTitleAndArticleType("test", ArticleType.QNA,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    @DisplayName("작성자 닉네임과 타입으로 게시글 조회")
    @Test
    void findByNicknameAndArticleType() {
        Writer writer1 = createWriter(1L, "member1", "member1");
        Writer writer2 = createWriter(2L, "member2", "member2");
        Writer writer3 = createWriter(3L, "member3", "member3");
        writerRepository.save(writer1);
        writerRepository.save(writer2);
        writerRepository.save(writer3);

        Article article1 = createArticle(writer1, "test", ArticleType.QNA);
        Article article2 = createArticle(writer2, "test", ArticleType.QNA);
        Article article3 = createArticle(writer3, "test", ArticleType.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        Page<ArticleDto> articles
                = articleRepository.findByNicknameAndArticleType("member2", ArticleType.QNA,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));

        assertThat(articles.getTotalElements()).isEqualTo(1);
        for (ArticleDto article : articles) {
            assertThat(article.getWriter()).isEqualTo("member2");
        }
    }

    @DisplayName("게시글 다음글 조회")
    @Test
    void findNextByIdAndArticleType() {
        Writer writer1 = createWriter(1L, "member1", "member1");
        writerRepository.save(writer1);

        Article article1 = createArticle(writer1,"test1", ArticleType.QNA);
        Article article2 = createArticle(writer1,"test2", ArticleType.QNA);
        Article article3 = createArticle(writer1,"test3", ArticleType.NOTICE);
        Article article4 = createArticle(writer1,"test4", ArticleType.QNA);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        Article article = articleRepository.findNextByIdAndArticleType(article2.getId(), ArticleType.QNA.name())
                .orElseThrow();

        assertThat(article.getId()).isEqualTo(article4.getId());
        assertThat(article.getTitle()).isEqualTo(article4.getTitle());
    }

    @DisplayName("게시글 이전글 조회")
    @Test
    void findPrevByIdAndArticleType() {
        Writer writer1 = createWriter(1L, "member1", "member1");
        writerRepository.save(writer1);

        Article article1 = createArticle(writer1,"test1", ArticleType.QNA);
        Article article2 = createArticle(writer1,"test2", ArticleType.QNA);
        Article article3 = createArticle(writer1,"test3", ArticleType.NOTICE);
        Article article4 = createArticle(writer1,"test4", ArticleType.QNA);
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