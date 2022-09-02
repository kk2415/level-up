package com.levelup.api.controller;

import com.levelup.api.service.*;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.Result;
import com.levelup.api.dto.comment.CommentResponse;
import com.levelup.api.dto.comment.CreateCommentRequest;
import com.levelup.api.dto.comment.CreateReplyCommentRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentApiController {

    private final CommentService commentService;

    /***
     * 댓글 생성
     */
    @PostMapping("/comments-for-test")
    public ResponseEntity<CommentResponse> create(@RequestBody @Validated CreateCommentRequest commentRequest,
                                                  @RequestParam("member") Long memberId) {
        CommentResponse response = commentService.save(commentRequest, memberId);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponse> create(@RequestBody @Validated CreateCommentRequest commentRequest,
                                                  @AuthenticationPrincipal Member member) {
        CommentResponse response = commentService.save(commentRequest, member.getId());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/comments/reply")
    public ResponseEntity<CommentResponse> createReplyComment(@RequestBody @Validated CreateReplyCommentRequest commentRequest,
                                                              @AuthenticationPrincipal Member member) {
        CommentResponse response = commentService.saveReplyComment(commentRequest, member.getId());

        return ResponseEntity.ok().body(response);
    }


    /**
     * 댓글 조회
     */
    @GetMapping("/comments/{articleId}")
    public ResponseEntity<Result<CommentResponse>> find(@PathVariable Long articleId,
                       @RequestParam ArticleType identity) {
        List<CommentResponse> response = commentService.getComments(articleId);

        return ResponseEntity.ok().body(new Result(response, response.size()));
    }

    @GetMapping("/comments/{commentId}/reply")
    public ResponseEntity<Result<CommentResponse>> findReply(@PathVariable Long commentId) {
        List<CommentResponse> response = commentService.getReplyCommentByParentId(commentId);

        return ResponseEntity.ok().body(new Result(response, response.size()));
    }


    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }
}
