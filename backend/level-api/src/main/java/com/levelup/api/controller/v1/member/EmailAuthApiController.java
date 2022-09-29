package com.levelup.api.controller.v1.member;

import com.levelup.member.domain.service.EmailAuthService;
import com.levelup.api.controller.v1.dto.request.member.EmailAuthRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이메일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email-auth")
public class EmailAuthApiController {

    private final EmailAuthService emailAuthService;


    /**
     * 인증번호 생성
     * */
    @PostMapping({"", "/"})
    public ResponseEntity<Void> create(
            @RequestBody EmailAuthRequest request,
            @RequestParam("email") String email)
    {
        emailAuthService.save(request.toDto(), email);

        return ResponseEntity.ok().build();
    }

    /**
     * 이메일 인증
     * */
    @PatchMapping({"", "/"})
    public ResponseEntity<Void> authenticateEmail(
            @RequestBody EmailAuthRequest request,
            @RequestParam("email") String email)
    {
        emailAuthService.authenticateEmail(request.toDto(), email);

        return ResponseEntity.ok().build();
    }
}
