package com.levelup.api.service;


import com.levelup.api.api.DateFormat;
import com.levelup.core.domain.comment.ArticleIdentity;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.comment.CommentResponse;
import com.levelup.core.dto.comment.CreateCommentRequest;
import com.levelup.core.dto.comment.CreateReplyCommentRequest;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.notice.NoticeRepository;
import com.levelup.core.repository.post.PostRepository;
import com.levelup.core.repository.qna.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;
    private final ChannelNoticeService channelNoticeService;
    private final QnaRepository qnaRepository;

    /**
     * 댓글 작성
     * */
    public CommentResponse create(CreateCommentRequest commentRequest, Long memeberId) {
        Member findMember = memberRepository.findById(memeberId);
        Object article = identifyArticle(commentRequest.getIdentity(), commentRequest.getArticleId());

        Comment findComment = Comment.createComment(findMember, article, commentRequest.getContent());
        commentRepository.save(findComment);

        return new CommentResponse(findComment.getId(), findComment.getWriter(), findComment.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findComment.getDateCreated()),
                findComment.getVoteCount(), (long) findComment.getChild().size());
    }

    private Object identifyArticle(ArticleIdentity identity, Long articleId) {
        switch (identity) {
            case POST: return postRepository.findById(articleId);
            case CHANNEL_NOTICE: return channelNoticeService.findById(articleId);
            case NOTICE: return noticeRepository.findById(articleId);
            case QNA: return qnaRepository.findById(articleId);
            default: throw new IllegalArgumentException("Unknown Article Identity");
        }
    }

    public CommentResponse createReplyComment(CreateReplyCommentRequest commentRequest, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Comment parent = commentRepository.findById(commentRequest.getParentId());
        Object article = identifyArticle(commentRequest.getIdentity(), commentRequest.getArticleId());

        Comment child = Comment.createComment(member, article, commentRequest.getContent());
        commentRepository.save(child);
        parent.addChildComment(child);

        return new CommentResponse(child.getId(), child.getWriter(), child.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(child.getDateCreated()),
                child.getVoteCount(), (long) child.getChild().size());
    }


    /**
     * 댓글 조회
     * */
    public List<CommentResponse> getAllByIdentityAndArticleId(ArticleIdentity identity, Long articleId) {
        List<Comment> comments;

        switch (identity) {
            case POST:
                comments = commentRepository.findByPostId(articleId);
                break;
            case CHANNEL_NOTICE:
                comments = commentRepository.findByChannelNoticeId(articleId);
                break;
            case NOTICE:
                comments = commentRepository.findByNoticeId(articleId);
                break;
            case QNA:
                comments = commentRepository.findByQnaId(articleId);
                break;
            default:
                throw new IllegalArgumentException("Unknown Article Identity");
        }

        return comments.stream()
                .filter(c -> c.getParent() == null)
                .map(c -> new CommentResponse(c.getId() ,c.getWriter(), c.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(c.getDateCreated()),
                        c.getVoteCount(), (long) c.getChild().size()))
                .collect(Collectors.toList());
    }

    public List<CommentResponse> findReplyById(Long commentId) {
        List<Comment> reply = commentRepository.findReplyById(commentId);

        return reply.stream()
                .map(c -> new CommentResponse(c.getId() ,c.getWriter(), c.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(c.getDateCreated()),
                        c.getVoteCount(), (long) c.getChild().size()))
                .collect(Collectors.toList());
    }

    public List<Comment> findByMemberId(Long memberId) {
        return commentRepository.findByMemberId(memberId);
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> findByNoticeId(Long noticeId) {
        return commentRepository.findByNoticeId(noticeId);
    }

    public List<Comment> findByChannelNoticeId(Long channelNoticeId) {
        return commentRepository.findByChannelNoticeId(channelNoticeId);
    }

    public List<Comment> findByQnaId(Long qnaId) {
        return commentRepository.findByQnaId(qnaId);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }


    /**
     * 댓글 수정
     * */
    public void updateComment(Long commentId, String content) {
        Comment findComment = commentRepository.findById(commentId);
        findComment.changeComment(content);
    }


    /**
     * 댓글 삭제
     * */
    public void deleteComment(Long commentId) {
        commentRepository.delete(commentId);
    }
}
