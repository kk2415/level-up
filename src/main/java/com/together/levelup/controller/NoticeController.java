package com.together.levelup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    /**
     * 생성
     * */
    @GetMapping("/create")
    public String create(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        if (request.getAttribute("isAdmin").equals(false)) {
            response.sendRedirect("/");
        }

        return "html/notice/createNotice";
    }


    /**
     * 조회
     * */
    @GetMapping("")
    public String notice(@RequestParam(required = false, defaultValue = "1") Long page,
                         @RequestParam(required = false) String field,
                         @RequestParam(required = false) String query,
                         HttpServletRequest request,
                         Model model) {

//        model.addAttribute("isAdmin", false);
//
//        if (request.getAttribute("isAdmin").equals(true)) {
//            model.addAttribute("isAdmin", true);
//        }

        return "html/notice/boardNotice";
    }


    /**
     * 수정
     * */
    @GetMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        if (request.getAttribute("isAdmin").equals(false)) {
            response.sendRedirect("/");
        }

        return "html/notice/updateNotice";
    }

}
