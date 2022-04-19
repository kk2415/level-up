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

    @PostMapping("/create")
    public String createPost() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginGet() {
        return "html/member/loginMember";
    }

    @PostMapping("/login")
    public String loginPost() {
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

}
