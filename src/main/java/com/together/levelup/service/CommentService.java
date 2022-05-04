package com.together.levelup.service;

import com.together.levelup.domain.comment.ArticleIdentity;
import com.together.levelup.domain.comment.Comment;
import com.together.levelup.domain.member.Member;
import com.together.levelup.repository.comment.CommentRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.notice.NoticeRepository;
import com.together.levelup.repository.post.PostRepository;
import com.together.levelup.repository.qna.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Long create(ArticleIdentity identity, Long memeberId, Long articleId, String content) {
        Member findMember = memberRepository.findById(memeberId);
        Object article = identifyArticle(identity, articleId);

        Comment comment = Comment.createComment(findMember, article, content);
        commentRepository.save(comment);
        return comment.getId();
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

    /**
     * 댓글 조회
     * */
    public Comment findOne(Long commentId) {
        return commentRepository.findById(commentId);
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

}
