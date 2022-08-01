package com.levelup.api.controller;

import com.levelup.api.service.ArticleService;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.article.ArticleRequest;
import com.levelup.core.dto.article.ArticleResponse;
import com.levelup.core.dto.article.ChannelPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ArticleApiController {

    private final ArticleService articleService;


    /**
     * 생성
     * */
    @PostMapping("/article")
    public ResponseEntity<ArticleResponse> create(@Validated @RequestBody ArticleRequest request,
                                                      @AuthenticationPrincipal Member member) {
        ArticleResponse response = articleService.save(request, member.getId());

        return ResponseEntity.ok().body(response);
    }


    /**
     * 조회
     * */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<ArticleResponse> getPost(@PathVariable Long articleId,
                                                   @RequestParam(required = false, defaultValue = "false") String view) {
        ArticleResponse response = articleService.getArticle(articleId, view);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articles")
    public ResponseEntity<Page<ArticleResponse>> getPosts(@RequestParam ArticleType articleType,
                                                          Pageable pageable,
                                                          @RequestParam(required = false) String field,
                                                          @RequestParam(required = false) String query) {
        Page<ArticleResponse> response = articleService.getArticles(articleType, field, query, pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/article/{articleId}/nextArticle")
    public ResponseEntity<ArticleResponse> findNextPost(@PathVariable Long articleId,
                                                        @RequestParam ArticleType articleType) {
        ArticleResponse response = articleService.getNextPageByArticleType(articleId, articleType);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/article/{articleId}/prevArticle")
    public ResponseEntity<ArticleResponse> findPrevPost(@PathVariable Long articleId,
                                                        @RequestParam ArticleType articleType) {
        ArticleResponse response = articleService.getPrevPageByArticleType(articleId, articleType);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/article/{articleId}/oauth")
    public ResponseEntity<Void> checkMember(@PathVariable Long articleId, @RequestParam Long memberId) {
        articleService.articleOauth(articleId, memberId);

        return ResponseEntity.ok().build();
    }


    /**
     * 수정
     * */
    @PatchMapping("/article/{articleId}")
    public ResponseEntity<ArticleResponse> mopdify(@PathVariable Long articleId,
                                                   @RequestBody ChannelPostRequest request,
                                                   @AuthenticationPrincipal Member member) {
        ArticleResponse response = articleService.modify(articleId, member.getId(), request);

        return ResponseEntity.ok().body(response);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);

        return ResponseEntity.ok().build();
    }
}
