package com.levelup.api.controller;

import com.levelup.api.dto.service.channelPost.ChannelPostDto;
import com.levelup.api.service.ChannelPostService;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.request.channelPost.ChannelPostRequest;
import com.levelup.api.dto.response.channelPost.ChannelPostResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "채널 게시글 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChannelPostApiController {

    private final ChannelPostService channelPostService;

    @PostMapping("/channel-posts")
    public ResponseEntity<ChannelPostResponse> create(
            @Validated @RequestBody ChannelPostRequest request,
            @RequestParam("channel") Long channelId,
            @RequestParam("member") Long memberId)
    {
        ChannelPostDto dto = channelPostService.save(request.toDto(), memberId, channelId);

        return ResponseEntity.ok().body(ChannelPostResponse.from(dto));
    }



    @GetMapping("/channel-posts/{articleId}")
    public ResponseEntity<ChannelPostResponse> get(
            @PathVariable Long articleId,
            @RequestParam(required = false, defaultValue = "false") boolean view)
    {
        ChannelPostDto dto = channelPostService.get(articleId, view);

        return ResponseEntity.ok().body(ChannelPostResponse.from(dto));
    }

    @GetMapping("/channel-posts")
    public ResponseEntity<Page<ChannelPostResponse>> getByPaging(
            @RequestParam("channel") Long channelId,
            @RequestParam ArticleType articleType,
            Pageable pageable,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String query)
    {
        Page<ChannelPostResponse> response
                = channelPostService.getByPaging(channelId, articleType, field, query, pageable)
                            .map(ChannelPostResponse::from);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/channel-posts/{articleId}/next-post")
    public ResponseEntity<ChannelPostResponse> getNext(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType,
            @RequestParam("channel") Long channelId)
    {
        ChannelPostDto dto = channelPostService.getNext(articleId, articleType, channelId);

        return ResponseEntity.ok().body(ChannelPostResponse.from(dto));
    }

    @GetMapping("/channel-posts/{articleId}/prev-post")
    public ResponseEntity<ChannelPostResponse> getPrev(
            @PathVariable Long articleId,
            @RequestParam ArticleType articleType,
            @RequestParam("channel") Long channelId)
    {
        ChannelPostDto dto = channelPostService.getPrev(articleId, articleType, channelId);

        return ResponseEntity.ok().body(ChannelPostResponse.from(dto));
    }



    @PatchMapping("/channel-posts-for-test/{articleId}")
    public ResponseEntity<ChannelPostResponse> update(
            @PathVariable Long articleId,
            @RequestBody ChannelPostRequest request,
            @RequestParam Long memberId)
    {
        ChannelPostDto dto = channelPostService.update(request.toDto(), articleId, memberId);

        return ResponseEntity.ok().body(ChannelPostResponse.from(dto));
    }

    @PatchMapping("/channel-posts/{articleId}")
    public ResponseEntity<ChannelPostResponse> update(
            @PathVariable Long articleId,
            @RequestBody ChannelPostRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Member member)
    {
        ChannelPostDto dto = channelPostService.update(request.toDto(), articleId, member.getId());

        return ResponseEntity.ok().body(ChannelPostResponse.from(dto));
    }



    @DeleteMapping("/channel-posts/{articleId}")
    public ResponseEntity<Void> delete(@PathVariable Long articleId) {
        channelPostService.delete(articleId);

        return ResponseEntity.ok().build();
    }
}
