package com.together.levelup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/create")
    public String createGet() {
        return "html/member/createMember";
    }

    @GetMapping("/login")
    public String loginGet(@RequestParam String redirectURL) {
        return "html/member/loginMember";
    }

    @GetMapping("/logout")
    public String loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPage(HttpServletRequest request) {
        return "html/member/myPage";
    }

}
