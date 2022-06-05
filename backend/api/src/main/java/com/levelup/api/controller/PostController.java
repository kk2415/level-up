package com.levelup.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostController {

    @GetMapping("/create")
    public String createPost(@RequestParam Long channel) {
        return "html/post/createPost";
    }

    @GetMapping("/detail/{postId}")
    public String detailPost(@RequestParam Long channel) {
        return "html/post/detailPost";
    }

    @GetMapping("/edit/{postId}")
    public String updatePost(@RequestParam String email, @RequestParam(required = false) Long channel) {
        return "html/post/updatePost";
    }

}
