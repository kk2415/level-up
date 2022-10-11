package com.levelup.article.repository;

import com.levelup.article.ArticleApplicationTest;
import com.levelup.article.TestSupporter;
import com.levelup.article.config.TestJpaConfig;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.Comment;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.CommentRepository;
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

@DisplayName("게시글 댓글 레포지토리 테스트")
@ActiveProfiles("test")
@Transactional
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ArticleApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest extends TestSupporter {

    @Autowired WriterRepository writerRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired CommentRepository commentRepository;

    @BeforeEach
    public void before() {
        writerRepository.deleteAll();
        articleRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @DisplayName("대댓글 조회")
    @Test
    void findReplyByParentId() {
        // Given
        Writer writer1 = createWriter(1L, "test", "test");
        writerRepository.save(writer1);

        Article article1 = createArticle(writer1, "test article1", ArticleType.QNA);
        articleRepository.save(article1);

        Comment comment1 = createComment(writer1, article1);
        Comment comment2 = createComment(writer1, article1);
        Comment replyComment = createReplyComment(writer1, article1, comment1);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(replyComment);

        // When
        List<Comment> replyComments1 = commentRepository.findReplyByParentId(comment1.getId());
        List<Comment> replyComments2 = commentRepository.findReplyByParentId(comment2.getId());

        // Then
        assertThat(replyComments1.size()).isEqualTo(1);
        assertThat(replyComments2.size()).isEqualTo(0);
    }
}