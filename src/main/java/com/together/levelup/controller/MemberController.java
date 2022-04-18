package com.together.levelup.controller;

import com.together.levelup.dto.CreateMemberRequest;
import com.together.levelup.dto.MemberJoinForm;
import com.together.levelup.domain.member.Gender;
import com.together.levelup.exception.DuplicateEmailException;
import com.together.levelup.service.LoginService;
import com.together.levelup.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @ModelAttribute("gender")
    public Gender[] genders() {
        return Gender.values();
    }

    @GetMapping("/create")
    public String create(Model model) {
        return "member/createMember";
    }

    @PostMapping("/create")
    public String create() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "member/loginMember";
    }

}
