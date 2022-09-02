package com.levelup.api.controller;

import com.levelup.api.service.EmailAuthService;
import com.levelup.api.dto.auth.EmailAuthRequest;
import com.levelup.api.dto.auth.EmailAuthResponse;
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
     * 이메일 인증
     * */
    @PatchMapping("/email")
    public ResponseEntity<EmailAuthResponse> confirmEmail(@RequestBody EmailAuthRequest request,
                                                          @RequestParam("member") Long memberId) {
        EmailAuthResponse response = emailAuthService.confirmEmail(request.getSecurityCode(), memberId);

        return ResponseEntity.ok().body(response);
    }


    /**
     * 인증번호 전송
     * */
    @PostMapping("/email")
    public ResponseEntity<Void> sendSecurityCode(@RequestParam("member") Long memberId) {
        emailAuthService.sendSecurityCode(memberId);

        return ResponseEntity.ok().build();
    }
}
