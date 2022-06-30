package com.levelup.api.controller;

import com.levelup.api.service.ChannelPostService;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.article.ChannelPostRequest;
import com.levelup.core.dto.article.ChannelPostResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelPostApiController {

    private final ChannelPostService channelPostService;


    /**
     * 생성
     * */
    @PostMapping("/channel-post")
    public ResponseEntity<ChannelPostResponse> create(@Validated @RequestBody ChannelPostRequest request,
                                                      @RequestParam("channel") Long channelId,
                                                      @AuthenticationPrincipal Member member) {
        ChannelPostResponse channelPost = channelPostService.create(request, member.getId(), channelId);

        return ResponseEntity.ok().body(channelPost);
    }

    /**
     * 조회
     * */
    @GetMapping("/channel-post/{articleId}")
    public ResponseEntity<ChannelPostResponse> getPost(@PathVariable Long articleId,
                                                       @RequestParam(required = false, defaultValue = "false") String view) {
        ChannelPostResponse channelPost = channelPostService.getChannelPost(articleId, view);

        return ResponseEntity.ok().body(channelPost);
    }

    @GetMapping("/channel-posts")
    public ResponseEntity<Page<ChannelPostResponse>> getPosts(@RequestParam("channel") Long channelId,
                                                              @RequestParam ArticleType articleType,
                                                              Pageable pageable,
                                                              @RequestParam(required = false) String field,
                                                              @RequestParam(required = false) String query) {
        Page<ChannelPostResponse> channelPosts = channelPostService.getChannelPosts(channelId, articleType, field,
                query, pageable);

        return ResponseEntity.ok().body(channelPosts);
    }

    @GetMapping("/channel-posts/{articleId}/nextPost")
    public ResponseEntity<ChannelPostResponse> findNextPost(@PathVariable Long articleId,
                                                            @RequestParam ArticleType articleType,
                                                            @RequestParam("channel") Long channelId) {
        ChannelPostResponse channelPost = channelPostService.findNextPage(articleId, articleType, channelId);

        return ResponseEntity.ok().body(channelPost);
    }

    @GetMapping("/channel-posts/{articleId}/prevPost")
    public ResponseEntity<ChannelPostResponse> findPrevPost(@PathVariable Long articleId,
                                                            @RequestParam ArticleType articleType,
                                                            @RequestParam("channel") Long channelId) {
        ChannelPostResponse channelPost = channelPostService.findPrevPage(articleId, articleType, channelId);

        return ResponseEntity.ok().body(channelPost);
    }

    @GetMapping("/channel-posts/{articleId}/oauth")
    public ResponseEntity checkMember(@PathVariable Long articleId, @RequestParam Long memberId) {
        channelPostService.articleOauth(articleId, memberId);

        return new ResponseEntity(new Result("인증 성공", 1), HttpStatus.OK);
    }


    /**
     * 수정
     * */
    @PatchMapping("/channel-posts/{articleId}")
    public ResponseEntity<ChannelPostResponse> updatePost(@PathVariable Long articleId,
                                     @RequestBody ChannelPostRequest request,
                                     @AuthenticationPrincipal Member member) {
        ChannelPostResponse channelPost = channelPostService.modify(articleId, member.getId(), request);

        return ResponseEntity.ok().body(channelPost);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel-posts/{articleId}")
    public ResponseEntity<Result> deletePost(@PathVariable Long articleId) {
        channelPostService.delete(articleId);

        return new ResponseEntity(new Result("게시물이 성공적으로 삭제되었습니다.", 1), HttpStatus.OK);
    }

}
