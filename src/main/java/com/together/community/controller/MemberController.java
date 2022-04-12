package com.together.community.controller;

import com.together.community.controller.dto.MemberJoinForm;
import com.together.community.domain.member.Gender;
import com.together.community.exception.DuplicateEmailException;
import com.together.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ModelAttribute("gender")
    public Gender[] genders() {
        return Gender.values();
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("member", new MemberJoinForm());
        return "member/memberRegister";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("member") MemberJoinForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "member/memberRegister";
        }

        try {
            memberService.join(memberForm);
        } catch (DuplicateEmailException e) {
            bindingResult.addError(new FieldError("member", "emailId", "중복된 회원입니다."));
            return "member/memberRegister";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "member/memberLogin";
    }

}
