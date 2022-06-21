package com.levelup.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleApiController {

//    private final ArticleService articleService;
//
//    @PostMapping("/article")
//    public ResponseEntity create(@RequestBody CreateArticleRequest request,
//                                 @AuthenticationPrincipal Long memberId) {
//        ArticleResponse articleResponse = articleService.create(request, memberId);
//
//        return ResponseEntity.ok().body(articleResponse);
//    }

}
