package com.levelup.article.repository;

import com.levelup.article.ArticleApplicationTest;
import com.levelup.article.TestSupporter;
import com.levelup.article.config.TestJpaConfig;
import com.levelup.article.domain.entity.*;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.CommentRepository;
import com.levelup.article.domain.repository.CommentVoteRepository;
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

@DisplayName("게시글 댓글 추천 레포지토리 테스트")
@ActiveProfiles("test")
@Transactional
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ArticleApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentVoteRepositoryTest extends TestSupporter {

    @Autowired WriterRepository writerRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired CommentVoteRepository commentVoteRepository;
    @Autowired CommentRepository commentRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        commentRepository.deleteAll();
        commentVoteRepository.deleteAll();
    }

    @DisplayName("게시글 댓글 추천 조회")
    @Test
    void findByMemberIdAndCommentId() {
        // Given
        Writer writer1 = createWriter(1L, "test", "test");
        Writer writer2 = createWriter(2L, "test", "test");
        writerRepository.save(writer1);
        writerRepository.save(writer2);

        Article article1 = createArticle(writer1, "test article1", ArticleType.QNA);
        Article article2 = createArticle(writer2, "test article2", ArticleType.QNA);
        articleRepository.save(article1);
        articleRepository.save(article2);

        Comment comment1 = createComment(writer1, article1);
        Comment comment2 = createComment(writer2, article2);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        CommentVote commentVote1 = createCommentVote(comment1, writer1.getMemberId());
        CommentVote commentVote2 = createCommentVote(comment2, writer1.getMemberId());
        CommentVote commentVote3 = createCommentVote(comment1, writer1.getMemberId());
        commentVoteRepository.save(commentVote1);
        commentVoteRepository.save(commentVote2);
        commentVoteRepository.save(commentVote3);

        // When
        List<CommentVote> commentVotes
                = commentVoteRepository.findByMemberIdAndCommentId(writer1.getMemberId(), comment1.getId());

        // Then
        assertThat(commentVotes.get(0).getComment()).isEqualTo(comment1);
        assertThat(commentVotes.get(0).getMemberId()).isEqualTo(writer1.getMemberId());
    }
}