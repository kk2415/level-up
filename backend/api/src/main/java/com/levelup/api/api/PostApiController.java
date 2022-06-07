package com.levelup.api.api;

import com.levelup.api.service.MemberService;
import com.levelup.api.service.PostService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.post.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;


    /**
     * 생성
     * */
    @PostMapping("/post")
    public ResponseEntity<PostResponse> create(@Validated @RequestBody CreatePostRequest postRequest,
                                 @AuthenticationPrincipal Long memberId) {
        PostResponse post = postService.create(postRequest, memberId);

        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/post/files")
    public ResponseEntity<UploadFile> createFile(MultipartFile file) throws IOException {
        UploadFile findFile = postService.createFileByMultiPart(file);

        return ResponseEntity.ok().body(findFile);
    }


    /**
     * 조회
     * */
    @GetMapping("/post/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId,
                                @RequestParam(required = false, defaultValue = "false") String view) {
        PostResponse findPost = postService.getPost(postId, view);

        return ResponseEntity.ok().body(findPost);
    }

    @GetMapping("/{channelId}/posts/{page}")
    public ResponseEntity getPosts(@PathVariable Long channelId,
                           @PathVariable int page,
                           @RequestParam(required = false, defaultValue = "10") int postCount,
                           @RequestParam(required = false) String field,
                           @RequestParam(required = false) String query) {
        List<PostResponse> posts = postService.getPosts(channelId, page, postCount, field, query);

        return ResponseEntity.ok().body(new Result(posts, posts.size()));
    }

    @GetMapping("/{channelId}/search/count")
    public ResponseEntity getPostsCount(@PathVariable Long channelId,
                                       @RequestParam(required = false) String field,
                                       @RequestParam(required = false) String query) {
        Long count = postService.getPostsCount(channelId, field, query);

        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/post/{postId}/nextPost")
    public ResponseEntity findNextPost(@PathVariable Long postId) {
        PostResponse post = postService.findNextPage(postId);

        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/post/{postId}/prevPost")
    public ResponseEntity findPrevPost(@PathVariable Long postId) {
        PostResponse post = postService.findPrevPage(postId);

        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/post/{postId}/check-member")
    public ResponseEntity checkMember(@PathVariable Long postId, @RequestParam String email) {
        postService.oauth(postId, email);

        return new ResponseEntity(new Result("인증 성공", 1), HttpStatus.OK);
    }


    /**
     * 수정
     * */
    @PatchMapping("/post/{postId}")
    public ResponseEntity updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest postRequest,
                                         @AuthenticationPrincipal Long memberId) {
        UpdatePostResponse post = postService.updatePost(postId, memberId, postRequest.getTitle(),
                postRequest.getContent(), postRequest.getCategory());

        return ResponseEntity.ok().body(post);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return new ResponseEntity(new Result("게시물이 성공적으로 삭제되었습니다.", 1), HttpStatus.OK);
    }

}
