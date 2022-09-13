package com.levelup.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.api.ApiApplication;
import com.levelup.api.dto.service.member.MemberDto;
import com.levelup.api.dto.response.member.MemberResponse;
import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.api.dto.request.member.MemberRequest;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@DisplayName("API 컨트롤러 - 회원가입")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest(classes = ApiApplication.class)
class SignUpApiControllerTest {

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
        MemberRequest request = MemberRequest.of("test@email.com", "pwd", "test",
                "test", Gender.MALE, LocalDate.now(), "010", new UploadFile("", ""));

        MemberResponse response = MemberResponse.from(MemberDto.from(request.toEntity()));
//        given(memberService.save(any())).willReturn(response);

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
                            fieldWithPath("email").description("member email"),
                            fieldWithPath("password").description("member password"),
                            fieldWithPath("name").description("member name"),
                            fieldWithPath("nickname").description("member nickname"),
                            fieldWithPath("gender").description("member gender"),
                            fieldWithPath("birthday").description("member birthday"),
                            fieldWithPath("phone").description("member phone"),
                            fieldWithPath("uploadFile.uploadFileName").description("file name"),
                            fieldWithPath("uploadFile.storeFileName").description("path on file stored")
                        ),
                        responseFields(
                                fieldWithPath("id").description("member entity PK").type("long"),
                                fieldWithPath("email").description("member email"),
                                fieldWithPath("password").description("member password"),
                                fieldWithPath("name").description("member name"),
                                fieldWithPath("nickname").description("member nickname"),
                                fieldWithPath("gender").description("member gender"),
                                fieldWithPath("birthday").description("member birthday"),
                                fieldWithPath("phone").description("member phone")
                        )
                ));
    }
}