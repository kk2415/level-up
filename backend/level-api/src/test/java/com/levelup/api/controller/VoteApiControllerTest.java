package com.levelup.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.TestSupporter;
import com.levelup.api.config.SecurityConfig;
import com.levelup.api.config.TestJpaConfig;
import com.levelup.api.controller.v1.article.VoteApiController;
import com.levelup.api.controller.v1.dto.request.article.VoteRequest;
import com.levelup.api.filter.JwtAuthenticationFilter;
import com.levelup.article.domain.entity.Writer;
import com.levelup.article.domain.service.dto.VoteDto;
import com.levelup.article.domain.service.vote.VoteServiceImpl;
import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.entity.VoteType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API 컨트롤러 - 추천")
@ActiveProfiles("test")
@Import({TestJpaConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(
        controllers = VoteApiController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthenticationFilter.class}))
class VoteApiControllerTest extends TestSupporter {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private VoteServiceImpl voteServiceImpl;

    @DisplayName("게시글 추천 테스트")
    @Test
    void create() throws Exception {
        Writer writer = createWriter(1L, 1L, "test", "test");
        Article article = createArticle(1L, writer, "test article", ArticleType.QNA);
        VoteRequest voteRequest = VoteRequest.of(writer.getMemberId(), article.getId(), VoteType.ARTICLE);

        given(voteServiceImpl.save(any(VoteDto.class))).willReturn(voteRequest.toDto());

        mvc.perform(post("/api/v1/votes")
                .contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(voteRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}