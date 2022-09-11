package com.levelup.core.repository;

import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.api.dto.article.ArticlePagingResponse;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.article.ArticlePagingDto;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@DisplayName("채널 레포지토리 테스트")
@Transactional
@SpringBootTest(classes = ApiApplication.class)
@ExtendWith(SpringExtension.class)
public class ArticleRepositoryTest extends TestSupporter {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleRepositoryTest(@Autowired MemberRepository memberRepository,
                                 @Autowired ArticleRepository articleRepository,
                                 @Autowired CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @AfterEach
    public void setup() {
        memberRepository.deleteAll();
        articleRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @DisplayName("아티클 제목과 아티클 타입으로 페이징 조회")
    @Test
    void findByTitleAndArticleType() {
        // Given
        Member member1 = createMember("test1@test.com", "test1");
        memberRepository.save(member1);

        Article article1 = createArticle(member1, "test article1", ArticleType.NOTICE);
        Article article2 = createArticle(member1, "test article2", ArticleType.NOTICE);
        Article article3 = createArticle(member1, "hello", ArticleType.NOTICE);
        Article article4 = createArticle(member1, "test article4", ArticleType.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        // When
        Page<ArticlePagingDto> articlePage = articleRepository.findByTitleAndArticleType(
                "test", ArticleType.NOTICE.name(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "article_id")));

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "article_id"));

        // Then
        Assertions.assertThat(articlePage.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("회원 닉네임과 아티클 타입으로 페이징 조회")
    @Test
    void findByNicknameAndArticleType() {
        // Given
        Member member1 = createMember("test1@test.com", "test1");
        Member member2 = createMember("test2@test.com", "test2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Article article1 = createArticle(member1, "test article1", ArticleType.NOTICE);
        Article article2 = createArticle(member1, "test article2", ArticleType.NOTICE);
        Article article3 = createArticle(member1, "test article3", ArticleType.NOTICE);
        Article article4 = createArticle(member2, "test article4", ArticleType.NOTICE);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        // When
        Page<ArticlePagingDto> articlePage = articleRepository.findByNicknameAndArticleType(
                "test", ArticleType.NOTICE.name(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "article_id")));

        // Then
        Assertions.assertThat(articlePage.getTotalElements()).isEqualTo(4);
    }

    @DisplayName("아티클 타입으로 페이징 조회")
    @Test
    void findByArticleTypeNativeQuery() {
        // Given
        Member member1 = createMember("test1@test.com", "test1");
        memberRepository.save(member1);

        Article article1 = createArticle(member1, "test article1", ArticleType.NOTICE);
        Article article2 = createArticle(member1, "test article2", ArticleType.NOTICE);
        Article article3 = createArticle(member1, "test article3", ArticleType.NOTICE);
        Article article4 = createArticle(member1, "test article4", ArticleType.QNA);
        Article article5 = createArticle(member1, "test article4", ArticleType.CHANNEL_POST);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);
        articleRepository.save(article5);

        Comment comment1 = createComment(member1, article1);
        Comment comment2 = createComment(member1, article1);
        Comment comment3 = createComment(member1, article2);
        Comment comment4 = createComment(member1, article3);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);

        // When
        Page<ArticlePagingDto> result = articleRepository.findByArticleType(
                ArticleType.NOTICE.name(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "article_id"))
        );
        List<ArticlePagingResponse> collect = result.map(ArticlePagingResponse::from)
                .stream()
                .collect(Collectors.toList());

        // Then
        Assertions.assertThat(collect.size()).isEqualTo(3);
        Assertions.assertThat(collect.get(0).getCommentCount()).isEqualTo(2);
        Assertions.assertThat(collect.get(1).getCommentCount()).isEqualTo(1);
        Assertions.assertThat(collect.get(2).getCommentCount()).isEqualTo(1);
    }
}
