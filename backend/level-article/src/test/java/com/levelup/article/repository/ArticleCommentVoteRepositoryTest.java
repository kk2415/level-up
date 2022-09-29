package com.levelup.article.repository;

import com.levelup.article.ArticleApplicationTest;
import com.levelup.article.TestSupporter;
import com.levelup.article.config.TestJpaConfig;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleComment;
import com.levelup.article.domain.ArticleType;
import com.levelup.article.domain.entity.CommentVote;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.CommentRepository;
import com.levelup.article.domain.repository.CommentVoteRepository;
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

@DisplayName("게시글 댓글 추천 레포지토리 테스트")
@ActiveProfiles("test")
@Transactional
@Import(TestJpaConfig.class)
@DataJpaTest
@ContextConfiguration(classes = {ArticleApplicationTest.class, MemberApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleCommentVoteRepositoryTest extends TestSupporter {

    @Autowired MemberRepository memberRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired CommentVoteRepository commentVoteRepository;
    @Autowired CommentRepository commentRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        memberRepository.deleteAll();
        commentRepository.deleteAll();
        commentVoteRepository.deleteAll();
    }

    @DisplayName("게시글 댓글 추천 조회")
    @Test
    void findByMemberIdAndCommentId() {
        // Given
        Member member1 = createMember("test1", "test1");
        Member member2 = createMember("test2", "test2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Article article1 = createArticle(member1, "test article1", ArticleType.QNA);
        Article article2 = createArticle(member1, "test article2", ArticleType.QNA);
        articleRepository.save(article1);
        articleRepository.save(article2);

        ArticleComment comment1 = createComment(member1, article1);
        ArticleComment comment2 = createComment(member1, article2);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        CommentVote commentVote1 = createCommentVote(comment1, member1.getId());
        CommentVote commentVote2 = createCommentVote(comment2, member1.getId());
        CommentVote commentVote3 = createCommentVote(comment1, member2.getId());
        commentVoteRepository.save(commentVote1);
        commentVoteRepository.save(commentVote2);
        commentVoteRepository.save(commentVote3);

        // When
        List<CommentVote> commentVotes
                = commentVoteRepository.findByMemberIdAndCommentId(member1.getId(), comment1.getId());

        // Then
        assertThat(commentVotes.get(0).getComment()).isEqualTo(comment1);
        assertThat(commentVotes.get(0).getMemberId()).isEqualTo(member1.getId());
    }
}