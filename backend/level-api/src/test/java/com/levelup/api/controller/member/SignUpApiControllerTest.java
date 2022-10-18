package com.levelup.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.TestSupporter;
import com.levelup.api.config.SecurityConfig;
import com.levelup.api.config.TestJpaConfig;
import com.levelup.api.controller.v1.member.SignUpApiController;
import com.levelup.api.filter.JwtAuthenticationFilter;
import com.levelup.member.domain.service.dto.MemberDto;
import com.levelup.member.domain.service.MemberService;
import com.levelup.api.controller.v1.dto.request.member.MemberRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@DisplayName("API 컨트롤러 - 회원가입")
@ActiveProfiles("test")
@Import({TestJpaConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(
        controllers = SignUpApiController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthenticationFilter.class}))
class SignUpApiControllerTest extends TestSupporter {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private MemberService memberService;

//    @WithUserDetails(value = "kyunkim", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("회원가입 테스트")
//    @WithMockUser
    @Test
    void signUpTest() throws Exception {
        // Given
        MemberDto memberDto = MemberDto.from(createMember("test@gmail.com", "test1"));

        String jsonRequest = objectMapper.writeValueAsString(MemberRequest.from(memberDto));
        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",
                "profileImage",
                "image/jpeg",
                "profileImage".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile request = new MockMultipartFile(
                "request",
                "request",
                "application/json",
                jsonRequest.getBytes(StandardCharsets.UTF_8));
        given(memberService.save(any(), any())).willReturn(memberDto);

        // When & Then
        mvc.perform(
                multipart("/api/v1/sign-up")
                        .file(profileImage)
                        .file(request)
                        .contentType("multipart/form-data")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}