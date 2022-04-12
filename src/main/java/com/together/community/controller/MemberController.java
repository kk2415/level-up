package com.together.community.controller;

import com.together.community.controller.dto.MemberDto;
import com.together.community.domain.member.Gender;
import com.together.community.domain.member.Member;
import com.together.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("member", new MemberDto());
        model.addAttribute("gender", Gender.values());
        return "member/memberRegister";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            model.addAttribute("gender", Gender.values());
            return "member/memberRegister";
        }

        //성공로직
       Member member = Member.createMember(memberDto.getEmailId(), memberDto.getEmailDomain(), memberDto.getPassword(),
                memberDto.getName(), memberDto.getGender(), memberDto.getBirthday(),
                memberDto.getPhone());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "member/memberLogin";
    }

}
