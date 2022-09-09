package com.levelup.api.controller;

import com.levelup.api.service.EmailAuthService;
import com.levelup.api.dto.emailAuth.EmailAuthRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이메일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmailAuthApiController {

    private final EmailAuthService emailAuthService;


    /**
     * 인증번호 생성
     * */
    @PostMapping("/email-auth")
    public ResponseEntity<Void> save(@RequestBody EmailAuthRequest request,
                                     @RequestParam("email") String email) {
        emailAuthService.save(request, email);

        return ResponseEntity.ok().build();
    }

    /**
     * 이메일 인증
     * */
    @PatchMapping("/email-auth")
    public ResponseEntity<Void> authenticateEmail(@RequestBody EmailAuthRequest request,
                                                  @RequestParam("email") String email) {
        emailAuthService.authenticateEmail(request, email);

        return ResponseEntity.ok().build();
    }
}
