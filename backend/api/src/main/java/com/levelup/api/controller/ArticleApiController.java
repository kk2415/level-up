package com.levelup.api.controller;

import com.levelup.api.dto.article.ArticlePagingResponse;
import com.levelup.api.dto.article.ArticleUpdateResponse;
import com.levelup.api.service.ArticleService;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.api.dto.article.ArticleRequest;
import com.levelup.api.dto.article.ArticleResponse;
import com.levelup.api.dto.channelPost.ChannelPostRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> create(@Validated @RequestBody ArticleRequest request,
                                                  @RequestParam("member") Long memberId) {
        ArticleResponse response = articleService.save(request, memberId);

        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ArticleResponse> getPost(@PathVariable Long articleId,
                                                   @RequestParam(required = false, defaultValue = "false") boolean view) {
        ArticleResponse response = articleService.getArticle(articleId, view);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articles")
    public ResponseEntity<Page<ArticlePagingResponse>> getPosts(@RequestParam ArticleType articleType,
                                                                 Pageable pageable,
                                                                 @RequestParam(required = false) String field,
                                                                 @RequestParam(required = false) String query) {
        Page<ArticlePagingResponse> response = articleService.getArticles(articleType, field, query, pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articles/{articleId}/next-article")
    public ResponseEntity<ArticleResponse> findNextPost(@PathVariable Long articleId,
                                                        @RequestParam ArticleType articleType) {
        ArticleResponse response = articleService.getNextPageByArticleType(articleId, articleType);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articles/{articleId}/prev-article")
    public ResponseEntity<ArticleResponse> findPrevPost(@PathVariable Long articleId,
                                                        @RequestParam ArticleType articleType) {
        ArticleResponse response = articleService.getPrevPageByArticleType(articleId, articleType);

        return ResponseEntity.ok().body(response);
    }



    @PatchMapping("/articles/{articleId}")
    public ResponseEntity<ArticleUpdateResponse> modify(@PathVariable Long articleId,
                                                   @RequestParam("member") Long memberId,
                                                   @RequestBody ChannelPostRequest request) {
        ArticleUpdateResponse response = articleService.modify(articleId, memberId, request);

        return ResponseEntity.ok().body(response);
    }



    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);

        return ResponseEntity.ok().build();
    }
}
