package com.levelup.api.controller.v1;

import com.levelup.api.service.dto.comment.ReplyCommentDto;
import com.levelup.api.service.*;
import com.levelup.api.controller.v1.dto.response.comment.CommentResponse;
import com.levelup.api.controller.v1.dto.request.comment.CommentRequest;
import com.levelup.api.controller.v1.dto.request.comment.ReplyCommentRequest;
import com.levelup.api.service.dto.comment.CommentDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponse> create(
            @RequestBody @Validated CommentRequest request,
            @RequestParam("member") Long memberId)
    {
        CommentDto dto = commentService.save(request.toDto(), memberId);

        return ResponseEntity.ok().body(CommentResponse.from(dto));
    }

    @PostMapping("/comments/reply")
    public ResponseEntity<CommentResponse> createReply(
            @RequestBody @Validated ReplyCommentRequest request,
            @RequestParam("member") Long memberId)
    {
        ReplyCommentDto dto = commentService.saveReply(request.toDto(), memberId);

        return ResponseEntity.ok().body(CommentResponse.from(dto));
    }



    @GetMapping("/comments/{articleId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long articleId) {
        List<CommentResponse> responses = commentService.getComments(articleId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/comments/{commentId}/reply")
    public ResponseEntity<List<CommentResponse>> getReplyComments(@PathVariable Long commentId) {
        List<CommentResponse> responses = commentService.getReplyComments(commentId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(responses);
    }



    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);

        return ResponseEntity.ok().build();
    }
}
