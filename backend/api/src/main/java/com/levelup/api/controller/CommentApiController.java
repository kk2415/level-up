package com.levelup.api.controller;

import com.levelup.api.service.*;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.comment.CommentResponse;
import com.levelup.core.dto.comment.CreateCommentRequest;
import com.levelup.core.dto.comment.CreateReplyCommentRequest;
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
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    /***
     * 댓글 생성
     */
    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> create(@RequestBody @Validated CreateCommentRequest commentRequest,
                                                  @AuthenticationPrincipal Member member) {
        CommentResponse comment = commentService.create(commentRequest, member.getId());

        return ResponseEntity.ok().body(comment);
    }

    @PostMapping("/comment/reply")
    public ResponseEntity<CommentResponse> createReplyComment(@RequestBody @Validated CreateReplyCommentRequest commentRequest,
                                                              @AuthenticationPrincipal Member member) {
        CommentResponse replyComment = commentService.createReplyComment(commentRequest, member.getId());

        return ResponseEntity.ok().body(replyComment);
    }


    /**
     * 댓글 조회
     */
    @GetMapping("/comment/{articleId}")
    public ResponseEntity<Result<CommentResponse>> find(@PathVariable Long articleId,
                       @RequestParam ArticleType identity) {
        List<CommentResponse> comments = commentService.getComments(articleId);

        return ResponseEntity.ok().body(new Result(comments, comments.size()));
    }

    @GetMapping("/comment/{commentId}/reply")
    public ResponseEntity<Result<CommentResponse>> findReply(@PathVariable Long commentId) {
        List<CommentResponse> comments = commentService.findReplyById(commentId);

        return ResponseEntity.ok().body(new Result(comments, comments.size()));
    }

}
