package com.together.levelup.controller;

import com.together.levelup.api.SessionName;
import com.together.levelup.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionName.SESSION_NAME, required = false) Member member) {
//        if (member == null) {
//            return "html/home/home";
//        }
//
//        return "html/home/loginHome";
        return "html/home/home";
    }

}
