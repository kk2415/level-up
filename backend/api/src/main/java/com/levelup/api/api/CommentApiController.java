package com.levelup.api.api;

import com.levelup.api.service.*;
import com.levelup.core.domain.Article.ArticleCategory;
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
    private final MemberService memberService;

    private final PostService postService;
    private final ChannelNoticeService channelNoticeService;
    private final NoticeService noticeService;


    /***
     * 댓글 생성
     */
    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> create(@RequestBody @Validated CreateCommentRequest commentRequest,
                                 @AuthenticationPrincipal Long memberId) {
        CommentResponse comment = commentService.create(commentRequest, memberId);

        return ResponseEntity.ok().body(comment);
    }

    @PostMapping("/comment/reply")
    public ResponseEntity<CommentResponse> createReplyComment(@RequestBody @Validated CreateReplyCommentRequest commentRequest,
                                              @AuthenticationPrincipal Long memberId) {
        CommentResponse replyComment = commentService.createReplyComment(commentRequest, memberId);

        return ResponseEntity.ok().body(replyComment);
    }


    /**
     * 댓글 조회
     */
    @GetMapping("/comment/{articleId}")
    public ResponseEntity<Result<CommentResponse>> find(@PathVariable Long articleId,
                       @RequestParam ArticleCategory identity) {
        List<CommentResponse> comments = commentService.getAllByIdentityAndArticleId(identity, articleId);

        return ResponseEntity.ok().body(new Result(comments, comments.size()));
    }

    @GetMapping("/comment/{commentId}/reply")
    public ResponseEntity<Result<CommentResponse>> findReply(@PathVariable Long commentId) {
        List<CommentResponse> comments = commentService.findReplyById(commentId);

        return ResponseEntity.ok().body(new Result(comments, comments.size()));
    }

}
