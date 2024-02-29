package com.levelup.api.controller.v1.article;

import com.levelup.api.controller.v1.dto.response.article.ArticlePagingResponse;
import com.levelup.api.controller.v1.dto.response.article.ArticleUpdateResponse;
import com.levelup.article.domain.service.dto.SearchCondition;
import com.levelup.article.domain.service.dto.ArticleDto;
import com.levelup.article.domain.service.ArticleService;
import com.levelup.article.domain.entity.ArticleType;
import com.levelup.api.controller.v1.dto.request.article.ArticleRequest;
import com.levelup.api.controller.v1.dto.response.article.ArticleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping({"", "/"})
    public ResponseEntity<ArticleResponse> create(
            @Valid @RequestBody ArticleRequest request,
            @RequestParam("member") Long memberId
    ) {
        ArticleDto dto = articleService.save(request.toDto(), memberId);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }

    @GetMapping({"/{articleId}", "/{articleId}/"})
    public ResponseEntity<ArticleResponse> get(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType,
            @RequestParam(required = false, defaultValue = "false") boolean view
    ) {
        ArticleDto dto = articleService.get(articleId, articleType, view);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<ArticlePagingResponse>> getArticles(
            @RequestParam ArticleType articleType,
            Pageable pageable,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String query
    ) {
        Page<ArticlePagingResponse> response
                = articleService.getArticles(articleType, SearchCondition.of(field, query), pageable)
                    .map(ArticlePagingResponse::from);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping({"/{articleId}/next-article", "/{articleId}/next-article/"})
    public ResponseEntity<ArticleResponse> getNext(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType
    ) {
        ArticleDto dto = articleService.getNext(articleId, articleType);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }

    @GetMapping({"/{articleId}/prev-article", "/{articleId}/prev-article/"})
    public ResponseEntity<ArticleResponse> getPrev(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType
    ) {
        ArticleDto dto = articleService.getPrev(articleId, articleType);

        return ResponseEntity.ok().body(ArticleResponse.from(dto));
    }


    @PatchMapping({"/{articleId}", "/{articleId}/"})
    public ResponseEntity<ArticleUpdateResponse> update(
            @PathVariable Long articleId,
            @RequestParam("member") Long memberId,
            @Valid @RequestBody ArticleRequest request
    ) {
        ArticleDto dto = articleService.update(request.toDto(), articleId, memberId);

        return ResponseEntity.ok().body(ArticleUpdateResponse.from(dto));
    }


    @DeleteMapping({"/{articleId}", "/{articleId}/"})
    public ResponseEntity<Void> delete(
            @PathVariable Long articleId,
            @RequestParam("articleType") ArticleType articleType
    ) {
        articleService.delete(articleId, articleType);

        return ResponseEntity.ok().build();
    }
}
