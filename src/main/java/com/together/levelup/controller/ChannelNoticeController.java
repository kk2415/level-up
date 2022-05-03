package com.together.levelup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel-notice")
public class ChannelNoticeController {

    /**
     * 생성
     * */
    @GetMapping("/create")
    public String create(@RequestParam Long channel) {
        return "html/channel_notice/createChannelNotice";
    }

    /**
     * 조회
     * */
    @GetMapping("/find")
    public String find() {
        return "";
    }

    /**
     * 수정
     * */
    @GetMapping("/update")
    public String update() {
        return "";
    }

    /**
     * 삭제
     * */
    @GetMapping("/delete")
    public String delete() {
        return "";
    }

}
