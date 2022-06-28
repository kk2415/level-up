package com.levelup.api.controller;

import com.levelup.api.service.ArticleService;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.article.ArticleRequest;
import com.levelup.core.dto.article.ArticleResponse;
import com.levelup.core.dto.article.ChannelPostRequest;
import com.levelup.core.dto.article.ChannelPostResponse;
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
@RequestMapping("/api")
public class ArticleApiController {

    private final ArticleService articleService;


    /**
     * 생성
     * */
    @PostMapping("/article")
    public ResponseEntity<ArticleResponse> create(@Validated @RequestBody ArticleRequest request,
                                                      @AuthenticationPrincipal Member member) {
        ArticleResponse article = articleService.createArticle(request, member.getId());

        return ResponseEntity.ok().body(article);
    }


    /**
     * 조회
     * */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<ArticleResponse> getPost(@PathVariable Long articleId,
                                                   @RequestParam(required = false, defaultValue = "false") String view) {
        ArticleResponse article = articleService.getArticle(articleId, view);

        return ResponseEntity.ok().body(article);
    }

    @GetMapping("/articles")
    public ResponseEntity<Page<ArticleResponse>> getPosts(@RequestParam ArticleType articleType,
                                                          Pageable pageable,
                                                          @RequestParam(required = false) String field,
                                                          @RequestParam(required = false) String query) {
        Page<ArticleResponse> article = articleService.getArticles(articleType, field, query, pageable);

        return ResponseEntity.ok().body(article);
    }

    @GetMapping("/article/{articleId}/nextArticle")
    public ResponseEntity<ArticleResponse> findNextPost(@PathVariable Long articleId,
                                                        @RequestParam ArticleType articleType) {
        ArticleResponse article = articleService.findNextPageByArticleType(articleId, articleType);

        return ResponseEntity.ok().body(article);
    }

    @GetMapping("/article/{articleId}/prevArticle")
    public ResponseEntity<ArticleResponse> findPrevPost(@PathVariable Long articleId,
                                                        @RequestParam ArticleType articleType) {
        ArticleResponse article = articleService.findPrevPageByArticleType(articleId, articleType);

        return ResponseEntity.ok().body(article);
    }

    @GetMapping("/article/{articleId}/oauth")
    public ResponseEntity checkMember(@PathVariable Long articleId, @RequestParam Long memberId) {
        articleService.articleOauth(articleId, memberId);

        return new ResponseEntity(new Result("인증 성공", 1), HttpStatus.OK);
    }


    /**
     * 수정
     * */
    @PatchMapping("/article/{articleId}")
    public ResponseEntity<ArticleResponse> mopdify(@PathVariable Long articleId,
                                                   @RequestBody ChannelPostRequest request,
                                                   @AuthenticationPrincipal Member member) {
        ArticleResponse articleResponse = articleService.modifyArticle(articleId, member.getId(), request);

        return ResponseEntity.ok().body(articleResponse);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<Result> deletePost(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);

        return new ResponseEntity(new Result("게시물이 성공적으로 삭제되었습니다.", 1), HttpStatus.OK);
    }

}
