package com.together.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostController {

    @GetMapping("/edit/{postId}")
    public String updatePost(@RequestParam String email) {
        return "html/post/updatePost";
    }

//    @GetMapping("/{postId}")
//    public String deletePost(@RequestParam String email) {
//        return "html/post/detailPost";
//    }

}
