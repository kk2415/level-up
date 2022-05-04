package com.together.levelup.api;

import com.together.levelup.domain.comment.ArticleIdentity;
import com.together.levelup.domain.comment.Comment;
import com.together.levelup.domain.member.Member;
import com.together.levelup.dto.comment.CommentResponse;
import com.together.levelup.dto.comment.CreateCommentRequest;
import com.together.levelup.dto.Result;
import com.together.levelup.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApiController {
    private final CommentService commentService;
    private final MemberService memberService;

    private final PostService postService;
    private final ChannelNoticeService channelNoticeService;
    private final NoticeService noticeService;
//    private final QnaService qnaService;


    /***
     * 댓글 생성
     */
    @PostMapping("/comment")
    public CommentResponse create(@RequestBody @Validated CreateCommentRequest commentRequest) {
        Member findMember = memberService.findByEmail(commentRequest.getMemberEmail());

        Long commentId = commentService.create(commentRequest.getIdentity(), findMember.getId(),
                commentRequest.getArticleId(), commentRequest.getContent());

        Comment findComment = commentService.findOne(commentId);
        return new CommentResponse(findComment.getWriter(), findComment.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findComment.getDateCreated()),
                findComment.getVoteCount());
    }

    /***
     * 댓글 조회
     */
    @GetMapping("/comment/{articleId}")
    public Result findByPostId(@PathVariable Long articleId,
                               @RequestParam ArticleIdentity identity) {
        List<Comment> findComments = identifyArticle(identity, articleId);

        List<CommentResponse> comments = findComments.stream().map(c -> new CommentResponse(c.getWriter(), c.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(c.getDateCreated()),
                c.getVoteCount())).collect(Collectors.toList());

        return new Result(comments, comments.size());
    }

    private List<Comment> identifyArticle(ArticleIdentity identity, Long articleId) {
        switch (identity) {
            case POST: return commentService.findByPostId(articleId);
            case CHANNEL_NOTICE: return commentService.findByChannelNoticeId(articleId);
            case NOTICE: return commentService.findByNoticeId(articleId);
            case QNA: return commentService.findByQnaId(articleId);
            default: throw new IllegalArgumentException("Unknown Article Identity");
        }
    }

}
