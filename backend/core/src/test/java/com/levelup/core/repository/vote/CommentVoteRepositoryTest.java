package com.levelup.core.repository.vote;

import com.levelup.core.TestSupporter;
import com.levelup.core.config.TestJpaConfig;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.CommentVote;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
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
class CommentVoteRepositoryTest extends TestSupporter {

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

        Comment comment1 = createComment(member1, article1);
        Comment comment2 = createComment(member1, article2);
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