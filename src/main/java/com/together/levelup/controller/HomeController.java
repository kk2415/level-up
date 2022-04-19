package com.together.levelup.controller;

import com.together.levelup.api.SesstionName;
import com.together.levelup.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SesstionName.SESSION_NAME, required = false) Member member) {

        if (member == null) {
            return "home/home";
        }
        return "home/loginHome";

    }

}
