package com.levelup.core.repository.comment;

import com.levelup.core.TestSupporter;
import com.levelup.core.config.TestJpaConfig;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
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
class CommentRepositoryTest extends TestSupporter {

    @Autowired MemberRepository memberRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired CommentRepository commentRepository;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        memberRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    void findReplyByParentId() {
        // Given
        Member member1 = createMember("test1", "test1");
        memberRepository.save(member1);

        Article article1 = createArticle(member1, "test article1", ArticleType.QNA);
        articleRepository.save(article1);

        Comment comment1 = createComment(member1, article1);
        Comment comment2 = createComment(member1, article1);
        Comment replyComment = createReplyComment(member1, article1, comment1);
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