package com.levelup.api.controller.member;

import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.dto.member.CreateMemberRequest;
import com.levelup.core.dto.member.CreateMemberResponse;
import com.levelup.core.repository.member.MemberRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignUpApiController {

    private final MemberService memberService;


    /**
     * 생성
     * */
    @PostMapping("/member")
    public ResponseEntity create(HttpServletRequest request,
                                 @RequestBody @Valid CreateMemberRequest memberRequest) throws IOException {
        CreateMemberResponse response = memberService.create(memberRequest);

        log.info("회원가입 성공 = url : {}, 이메일 : {}, 본명 : {}", request.getRequestURL(), memberRequest.getEmail(), memberRequest.getName());
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
