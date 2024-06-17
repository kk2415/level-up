package com.levelup.api.controller.member;

import com.levelup.api.ApiApplication;
import com.levelup.member.domain.service.MemberService;
import com.levelup.common.util.file.UploadFile;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ActiveProfiles("test")
@DisplayName("API 컨트롤러 - 멤버")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest(classes = ApiApplication.class)
class MemberApiControllerTest {

    private final MockMvc mvc;
    @MockBean private MemberService memberService;

    public MemberApiControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }
}
