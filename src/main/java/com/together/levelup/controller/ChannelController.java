package com.together.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String detail() {
        return "html/channel/channelDetail";
    }

    @GetMapping("/{channelId}/posting")
    public String posting() {
        return "html/channel/createChannelPost";
    }
}
