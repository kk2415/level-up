package com.levelup.api.controller;

import com.levelup.api.service.ArticleService;
import com.levelup.core.dto.article.ArticleResponse;
import com.levelup.core.dto.article.CreateArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity create(@RequestBody CreateArticleRequest request,
                                 @AuthenticationPrincipal Long memberId) {
        ArticleResponse articleResponse = articleService.create(request, memberId);

        return ResponseEntity.ok().body(articleResponse);
    }

}
