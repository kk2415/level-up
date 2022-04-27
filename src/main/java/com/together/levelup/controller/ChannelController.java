package com.together.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/channel")
public class ChannelController {

    @GetMapping("/create")
    public String createGet() {
        return "html/channel/createChannel";
    }

    @PostMapping("/create")
    public String createPost() {
        return "redirect:/";
    }

    @GetMapping("/detail/{channelId}")
    public String detail(@RequestParam(required = false, defaultValue = "1") Long page,
                         @RequestParam(required = false) String field,
                         @RequestParam(required = false) String query) {

        return "html/channel/detailChannel";
    }

    @GetMapping("/{channelId}/posting")
    public String posting() {
        return "html/post/createPost";
    }

    @GetMapping("/{channelId}/post/{postId}")
    public String viewDetailPost() {
        return "html/post/detailPost";
    }

}
