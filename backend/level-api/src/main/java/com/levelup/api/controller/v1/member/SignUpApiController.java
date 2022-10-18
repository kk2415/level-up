package com.levelup.api.controller.v1.member;

import com.levelup.member.domain.service.dto.MemberDto;
import com.levelup.api.controller.v1.dto.response.member.MemberResponse;
import com.levelup.member.domain.service.MemberService;
import com.levelup.api.controller.v1.dto.request.member.MemberRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "회원가입 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/sign-up")
@RestController
public class SignUpApiController {

    private final MemberService memberService;

    @PostMapping(value = {"", "/"})
    public ResponseEntity<MemberResponse> test(
            @RequestPart(value = "request", required = false) @Valid MemberRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) throws IOException {
        MemberDto dto = memberService.save(request.toDto(), profileImage);

        log.info("회원가입 성공 = 이메일 : {}, 본명 : {}", request.getEmail(), request.getName());

        return ResponseEntity.ok().body(MemberResponse.from(dto));
    }
}
