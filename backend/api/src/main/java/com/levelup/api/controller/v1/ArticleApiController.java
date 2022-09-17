package com.levelup.api.controller.v1;

import com.levelup.api.controller.v1.dto.response.article.ArticlePagingResponse;
import com.levelup.api.controller.v1.dto.response.article.ArticleUpdateResponse;
import com.levelup.api.service.dto.article.ArticleDto;
import com.levelup.api.service.ArticleService;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.api.controller.v1.dto.request.article.ArticleRequest;
import com.levelup.api.controller.v1.dto.response.article.ArticleResponse;
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
    public ResponseEntity<ArticleResponse> create(
            @Validated @RequestBody ArticleRequest request,
            @RequestParam("member") Long memberId)
    {
        ArticleDto dto = articleService.save(request.toDto(), memberId);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }



    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ArticleResponse> get(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType,
            @RequestParam(required = false, defaultValue = "false") boolean view)
    {
        ArticleDto dto = articleService.get(articleId, articleType, view);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }

    @GetMapping("/articles")
    public ResponseEntity<Page<ArticlePagingResponse>> getArticles(
            @RequestParam ArticleType articleType,
            Pageable pageable,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String query)
    {
        Page<ArticlePagingResponse> response = articleService.getArticles(articleType, field, query, pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articles/{articleId}/next-article")
    public ResponseEntity<ArticleResponse> getNext(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType)
    {
        ArticleDto dto = articleService.getNext(articleId, articleType);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }

    @GetMapping("/articles/{articleId}/prev-article")
    public ResponseEntity<ArticleResponse> getPrev(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType)
    {
        ArticleDto dto = articleService.getPrev(articleId, articleType);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }



    @PatchMapping("/articles/{articleId}")
    public ResponseEntity<ArticleUpdateResponse> update(
            @PathVariable Long articleId,
            @RequestParam("member") Long memberId,
            @RequestBody ArticleRequest request)
    {
        ArticleDto dto = articleService.update(request.toDto(), articleId, memberId);

        return ResponseEntity.ok().body(ArticleUpdateResponse.from(dto));
    }



    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<Void> delete(@PathVariable Long articleId) {
        articleService.delete(articleId);

        return ResponseEntity.ok().build();
    }
}
