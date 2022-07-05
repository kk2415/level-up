package com.levelup.api.controller;

import com.levelup.api.service.EmailAuthService;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.auth.EmailAuthRequest;
import com.levelup.core.dto.auth.EmailAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailAuthApiController {

    private final EmailAuthService emailAuthService;


    /**
     * 이메일 인증
     * */
    @PostMapping("/confirm-email")
    public ResponseEntity confirmEmail(@RequestBody EmailAuthRequest request,
                                       @AuthenticationPrincipal Member member) {
        EmailAuthResponse response = emailAuthService.confirmEmail(request.getSecurityCode(), member.getId());

        return ResponseEntity.ok().body(response);
    }


    /**
     * 인증번호 전송
     * */
    @GetMapping("/send/auth-email")
    public ResponseEntity sendSecurityCode(@AuthenticationPrincipal Member member) {
        emailAuthService.sendSecurityCode(member.getId());

        return ResponseEntity.ok().build();
    }

}
