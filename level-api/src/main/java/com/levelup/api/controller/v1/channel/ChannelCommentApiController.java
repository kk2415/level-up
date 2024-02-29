package com.levelup.api.controller.v1.channel;

import com.levelup.api.controller.v1.dto.request.channel.ChannelCommentRequest;
import com.levelup.api.controller.v1.dto.response.channel.ChannelCommentResponse;
import com.levelup.channel.domain.service.ChannelCommentService;
import com.levelup.channel.domain.service.dto.ChannelCommentDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "채널 게시글 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel/comments")
public class ChannelCommentApiController {

    private final ChannelCommentService commentService;

    @PostMapping({"", "/"})
    public ResponseEntity<ChannelCommentResponse> create(
            @Valid @RequestBody ChannelCommentRequest request,
            @RequestParam("member") Long memberId,
            @RequestParam("article") Long articleId,
            @RequestParam("channel") Long channelId
    ) {
        ChannelCommentDto dto = commentService.save(request.toDto(), memberId, articleId, channelId);

        return ResponseEntity.ok().body(ChannelCommentResponse.from(dto));
    }

    @PostMapping({"/reply","/reply/"})
    public ResponseEntity<ChannelCommentResponse> createReply(
            @Valid @RequestBody ChannelCommentRequest request,
            @RequestParam("member") Long memberId,
            @RequestParam("parent") Long parentId
    ) {
        ChannelCommentDto dto = commentService.saveReply(request.toDto(), memberId, parentId);

        return ResponseEntity.ok().body(ChannelCommentResponse.from(dto));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<ChannelCommentResponse>> getComments(@RequestParam("article") Long articleId) {
        List<ChannelCommentResponse> responses = commentService.getComments(articleId).stream()
                .map(ChannelCommentResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping({"/{commentId}/reply","/{commentId}/reply/"})
    public ResponseEntity<List<ChannelCommentResponse>> getReplyComments(@PathVariable Long commentId) {
        List<ChannelCommentResponse> responses = commentService.getReplyComments(commentId).stream()
                .map(ChannelCommentResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping({"/{commentId}", "/{commentId}/"})
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);

        return ResponseEntity.ok().build();
    }
}
