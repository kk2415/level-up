package com.levelup.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.TestSupporter;
import com.levelup.api.ApiApplication;
import com.levelup.api.dto.service.member.MemberDto;
import com.levelup.api.dto.response.member.MemberResponse;
import com.levelup.api.service.MemberService;
import com.levelup.api.dto.request.member.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API 컨트롤러 - 회원가입")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = ApiApplication.class)
class SignUpApiControllerTest extends TestSupporter {

    private final MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private MemberService memberService;

    public SignUpApiControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("회원가입 테스트")
    @Test
    void sign_up_test() throws Exception {
        // Given
        MemberDto memberDto = MemberDto.from(createMember("test@gmail.com", "test1"));
        MemberRequest request = MemberRequest.from(memberDto);
        MemberResponse response = MemberResponse.from(memberDto);
        given(memberService.save(any())).willReturn(memberDto);

        // When & Then
        mvc.perform(
                post("/api/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "sign-up-api",
                        preprocessRequest(),
                        preprocessResponse(),
                        requestFields(
                            fieldWithPath("email").type(JsonFieldType.STRING).description("member email"),
                            fieldWithPath("password").type(JsonFieldType.STRING).description("member password"),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("member name"),
                            fieldWithPath("nickname").type(JsonFieldType.STRING).description("member nickname"),
                            fieldWithPath("gender").type(JsonFieldType.STRING).description("member gender"),
                            fieldWithPath("birthday").type(JsonFieldType.STRING).description("member birthday"),
                            fieldWithPath("phone").type(JsonFieldType.STRING).description("member phone"),
                            fieldWithPath("uploadFile").type(JsonFieldType.OBJECT).description("file name"),
                            fieldWithPath("uploadFile.uploadFileName").description("file name"),
                            fieldWithPath("uploadFile.storeFileName").description("path on file stored")
                        ),
                        responseFields(
                                fieldWithPath("id").description("member entity PK").type("long"),
                                fieldWithPath("email").description("member email"),
                                fieldWithPath("name").description("member name"),
                                fieldWithPath("nickname").description("member nickname"),
                                fieldWithPath("gender").description("member gender"),
                                fieldWithPath("birthday").description("member birthday"),
                                fieldWithPath("phone").description("member phone"),
                                fieldWithPath("uploadFile.uploadFileName").description("file name"),
                                fieldWithPath("uploadFile.storeFileName").description("path on file stored"),
                                fieldWithPath("roles").type(JsonFieldType.ARRAY).description("user roles")
                        )
                ));
    }
}