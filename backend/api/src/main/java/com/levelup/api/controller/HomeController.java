//package com.levelup.api.controller;
//
//import com.levelup.api.api.SessionName;
//import com.levelup.core.domain.member.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.SessionAttribute;
//
//@Controller
//@RequiredArgsConstructor
//public class HomeController {
//
//    @GetMapping("/")
//    public String home(@SessionAttribute(name = SessionName.SESSION_NAME, required = false) Member member) {
//        return "html/home/home";
//    }
//
//}
