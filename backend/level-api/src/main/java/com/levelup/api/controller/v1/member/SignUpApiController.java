package com.levelup.api.controller.v1.member;

import com.levelup.api.controller.v1.dto.response.member.CreateMemberResponse;
import com.levelup.member.domain.service.dto.CreateMemberDto;
import com.levelup.member.domain.service.MemberService;
import com.levelup.api.controller.v1.dto.request.member.MemberRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CreateMemberResponse> save(
            @RequestBody @Valid MemberRequest request
    ) throws IOException
    {
        CreateMemberDto dto = memberService.save(request.toDto());

        log.info("회원가입 성공 = 이메일 : {}, 본명 : {}", request.getEmail(), request.getName());

        return ResponseEntity.ok().body(CreateMemberResponse.from(dto));
    }
}