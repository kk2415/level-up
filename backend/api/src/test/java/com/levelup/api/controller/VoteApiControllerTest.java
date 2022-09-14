package com.levelup.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.api.dto.request.vote.VoteRequest;
import com.levelup.api.dto.service.vote.VoteDto;
import com.levelup.api.service.vote.VoteServiceImpl;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.VoteType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@DisplayName("API 컨트롤러 - 추천")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest(classes = ApiApplication.class)
class VoteApiControllerTest extends TestSupporter {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private VoteServiceImpl voteServiceImpl;

    @DisplayName("게시글 추천 테스트")
    @Test
    void create() throws Exception {
        Member member = createMember(1L, "test", "test");
        Article article = createArticle(1L, member, "test article", ArticleType.QNA);
        VoteRequest voteRequest = VoteRequest.of(member.getId(), article.getId(), VoteType.ARTICLE);

        given(voteServiceImpl.save(any(VoteDto.class))).willReturn(voteRequest.toDto());

        mvc.perform(post("/api/v1/votes")
                .contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(voteRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}