package com.levelup.api.controller.v1.article;

import com.levelup.article.domain.entity.ArticleType;
import com.levelup.article.domain.service.dto.ReplyCommentDto;
import com.levelup.api.controller.v1.dto.response.article.CommentResponse;
import com.levelup.api.controller.v1.dto.request.article.CommentRequest;
import com.levelup.api.controller.v1.dto.request.article.ReplyCommentRequest;
import com.levelup.article.domain.service.dto.CommentDto;
import com.levelup.article.domain.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "게시글 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping({"", "/"})
    public ResponseEntity<CommentResponse> create(
            @Valid @RequestBody CommentRequest request,
            @RequestParam("member") Long memberId
    ) {
        CommentDto dto = commentService.save(request.toDto(), memberId);

        return ResponseEntity.ok().body(CommentResponse.from(dto));
    }

    @PostMapping({"/reply", "/reply/"})
    public ResponseEntity<CommentResponse> createReply(
            @Valid @RequestBody ReplyCommentRequest request,
            @RequestParam("member") Long memberId
    ) {
        ReplyCommentDto dto = commentService.saveReply(request.toDto(), memberId);

        return ResponseEntity.ok().body(CommentResponse.from(dto));
    }

    @GetMapping({"/{articleId}", "/{articleId}/"})
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long articleId) {
        List<CommentResponse> responses = commentService.getComments(articleId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping({"/{commentId}/reply", "/{commentId}/reply/"})
    public ResponseEntity<List<CommentResponse>> getReplyComments(@PathVariable Long commentId) {
        List<CommentResponse> responses = commentService.getReplyComments(commentId).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(responses);
    }


    @DeleteMapping({"/{commentId}", "/{commentId}/"})
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            @RequestParam("articleType") ArticleType articleType
    ) {
        commentService.delete(commentId, articleType);

        return ResponseEntity.ok().build();
    }
}
