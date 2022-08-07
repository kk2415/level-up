package com.levelup.core.repository;

import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@DisplayName("댓글 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
@ExtendWith(SpringExtension.class)
public class CommentRepositoryTest extends TestSupporter {

    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        memberRepository.deleteAll();
        channelRepository.deleteAll();
        articleRepository.deleteAll();
        commentRepository.deleteAll();
    }

    public CommentRepositoryTest(
           @Autowired MemberRepository memberRepository,
           @Autowired ChannelRepository channelRepository,
           @Autowired ArticleRepository articleRepository,
           @Autowired CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.channelRepository = channelRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @DisplayName("답글 조회")
    @Test
    void findReplyById() {
        // Given
        Member member = createMember("test@test.com", "test");
        memberRepository.save(member);

        Article article1 = createArticle(member, "test article1", ArticleType.NOTICE);
        articleRepository.save(article1);

        Comment parentComment = createComment(member, article1);
        commentRepository.save(parentComment);

        Comment replyComment = createReplyComment(member, article1, parentComment);
        commentRepository.save(replyComment);

        // When
        List<Comment> replyComments = commentRepository.findReplyByParentId(parentComment.getId());

        // Then
        Assertions.assertThat(replyComments).isNotEmpty();
        Assertions.assertThat(replyComments.size()).isEqualTo(1);
        Assertions.assertThat(replyComments.get(0).getParent().getId()).isEqualTo(parentComment.getId());
    }

    @DisplayName("articleId로 댓글 조회 테스트")
    @Test
    void findByArticleId() {
        // Given
        Member member = createMember("test@test.com", "test");
        memberRepository.save(member);

        Article article1 = createArticle(member, "test article1", ArticleType.NOTICE);
        articleRepository.save(article1);

        Comment comment1 = createComment(member, article1);
        Comment comment2 = createComment(member, article1);
        Comment comment3 = createComment(member, article1);
        comment1.setArticle(article1);
        comment2.setArticle(article1);
        comment3.setArticle(article1);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        // When
        List<Comment> comments = commentRepository.findByArticleId(article1.getId());

        // Then
        Assertions.assertThat(comments.size()).isEqualTo(3);
    }
}
