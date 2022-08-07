package com.levelup.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.api.service.ArticleService;
import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.VoteType;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.vote.ArticleVoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@DisplayName("API 컨트롤러 - 추천")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
//@Import(TestSecurityConfig.class)
@SpringBootTest(classes = ApiApplication.class)
class VoteApiControllerTest extends TestSupporter {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final ArticleVoteRepository articleVoteRepository;

    public VoteApiControllerTest(@Autowired MemberRepository memberRepository, @Autowired ArticleRepository articleRepository,
                                 @Autowired ArticleService articleService, @Autowired ArticleVoteRepository articleVoteRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
        this.articleService = articleService;
        this.articleVoteRepository = articleVoteRepository;
    }

    @AfterEach
    public void afterEach() {
        memberRepository.deleteAll();
        articleRepository.deleteAll();
        articleVoteRepository.deleteAll();
    }

    @DisplayName("추천 중복 테스트")
    @WithMockUser
    @Test
    void create() throws Exception {
        Member member1 = createMember("test1@test.com", "test1");
        memberRepository.save(member1);

        Article article1 = createArticle(member1, "test article1", ArticleType.NOTICE);
        articleRepository.save(article1);

        CreateVoteRequest voteRequest1 = CreateVoteRequest.of(member1.getId(), article1.getId(), VoteType.ARTICLE);

        mvc.perform(post("/api/v1/votes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(voteRequest1)))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/votes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(voteRequest1)))
                .andExpect(status().isBadRequest());

        assertThat(articleVoteRepository.findAll().size()).isEqualTo(1);
    }
}