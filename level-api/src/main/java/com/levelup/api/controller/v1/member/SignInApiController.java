package com.levelup.api.controller.v1.member;

import com.levelup.common.exception.ErrorCode;
import com.levelup.member.exception.LoginFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/sign-in")
@RestController
public class SignInApiController {

    @GetMapping({"/failure", "/failure/"})
    public void handleLoginFailure(@RequestParam ErrorCode errorCode) {
        throw new LoginFailureException(errorCode);
    }
}
