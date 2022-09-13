package com.levelup.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.api.ApiApplication;
import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@DisplayName("API 컨트롤러 - 멤버")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest(classes = ApiApplication.class)
class MemberApiControllerTest {

    private final MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private MemberService memberService;

    public MemberApiControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("회원 프로필 등록")
    @Test
    void createProfileImage() throws Exception {
        // Given
        given(memberService.createProfileImage(any(MultipartFile.class))).willReturn(new UploadFile(
                "myProfile.png",
                "profile/2cdcf2f4-be3d-4a29-9163-70342d4a375e.png"
                ));

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "myProfile.png",
                "image/png",
                "<<binary data>>".getBytes());

        // When & Then
        mvc.perform(multipart("/api/v1/members/image")
                        .file(file))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "member-post-profile",
                        requestPartBody("file"),
                        responseFields(
                                fieldWithPath("uploadFileName").description("file name"),
                                fieldWithPath("storeFileName").description("path on file stored")
                        )
                ));
    }

    @Test
    void getMember() {
    }

    @Test
    void getAllMembers() {
    }

    @Test
    void getProfileImage() {
    }

    @Test
    void confirmLogin() {
    }

    @Test
    void modifyMember() {
    }

    @Test
    void modifyMemberProfile() {
    }

    @Test
    void delete() {
    }
}