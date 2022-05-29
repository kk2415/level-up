package com.levelup.api.api;

import com.levelup.api.service.*;
import com.levelup.core.domain.comment.ArticleIdentity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.comment.CommentResponse;
import com.levelup.core.dto.comment.CreateCommentRequest;
import com.levelup.core.dto.comment.CreateReplyCommentRequest;
import com.levelup.core.exception.NotLoggedInException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
//    private final QnaService qnaService;


    /***
     * 댓글 생성
     */
    @PostMapping("/comment")
    public CommentResponse create(@RequestBody @Validated CreateCommentRequest commentRequest,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SessionName.SESSION_NAME) != null) {
            Member findMember = (Member)session.getAttribute(SessionName.SESSION_NAME);

            Long commentId = commentService.create(commentRequest.getIdentity(), findMember.getId(),
                    commentRequest.getArticleId(), commentRequest.getContent());

            Comment findComment = commentService.findOne(commentId);
            return new CommentResponse(findComment.getId(), findComment.getWriter(), findComment.getContent(),
                    DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findComment.getDateCreated()),
                    findComment.getVoteCount(), (long) findComment.getChild().size());
        }
        throw new NotLoggedInException("미인증 사용자");
    }

    @PostMapping("/comment/reply")
    public CommentResponse createReplyComment(@RequestBody @Validated CreateReplyCommentRequest commentRequest,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SessionName.SESSION_NAME) != null) {
            Member writer = (Member)session.getAttribute(SessionName.SESSION_NAME);

            Long commentId = commentService.createReplyComment(commentRequest.getParentId(), commentRequest.getIdentity(),
                    writer.getId(), commentRequest.getArticleId(), commentRequest.getContent());

            Comment findComment = commentService.findOne(commentId);
            return new CommentResponse(findComment.getId(), findComment.getWriter(), findComment.getContent(),
                    DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findComment.getDateCreated()),
                    findComment.getVoteCount(), (long) findComment.getChild().size());
        }
        throw new NotLoggedInException("미인증 사용자");
    }


    /***
     * 댓글 조회
     */
    @GetMapping("/comment/{articleId}")
    public Result find(@PathVariable Long articleId,
                       @RequestParam ArticleIdentity identity) {
        List<Comment> findComments = identifyArticle(identity, articleId);

        List<CommentResponse> comments = findComments.stream()
                .filter(c -> c.getParent() == null)
                .map(c -> new CommentResponse(c.getId() ,c.getWriter(), c.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(c.getDateCreated()),
                        c.getVoteCount(), (long) c.getChild().size())).collect(Collectors.toList());

        return new Result(comments, comments.size());
    }

    @GetMapping("/comment/{commentId}/reply")
    public Result findReply(@PathVariable Long commentId) {
        List<Comment> findComments = commentService.findReplyById(commentId);

        List<CommentResponse> comments = findComments.stream()
                .map(c -> new CommentResponse(c.getId() ,c.getWriter(), c.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(c.getDateCreated()),
                        c.getVoteCount(), (long) c.getChild().size())).collect(Collectors.toList());

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
