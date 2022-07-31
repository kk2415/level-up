package com.levelup.api.service;


import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.comment.CommentResponse;
import com.levelup.core.dto.comment.CreateCommentRequest;
import com.levelup.core.dto.comment.CreateReplyCommentRequest;
import com.levelup.core.exception.comment.CommentNotFoundException;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.exception.article.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;


    /**
     * 댓글 작성
     * */
    public CommentResponse save(CreateCommentRequest commentRequest, Long memberId) {
        Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

       final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
       final Comment findComment = commentRequest.toEntity(findMember, article);

       commentRepository.save(findComment);
       article.addCommentCount();

       return CommentResponse.from(findComment);
    }

    public CommentResponse saveReplyComment(CreateReplyCommentRequest commentRequest, Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));
        final Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
        Comment parentComment = commentRepository.findById(commentRequest.getParentId())
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
        Comment replyComment = commentRequest.toEntity(member, article);

        commentRepository.save(replyComment);
        parentComment.addChildComment(replyComment);

        return CommentResponse.from(replyComment);
    }


    /**
     * 댓글 조회
     * */
    public List<CommentResponse> getComments(Long articleId) {
       final List<Comment> comments = commentRepository.findByArticleId(articleId);

        return comments.stream()
                .filter(c -> c.getParent() == null)
                .map(CommentResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<CommentResponse> getReplyCommentByParentId(Long commentId) {
        final List<Comment> reply = commentRepository.findReplyByParentId(commentId);

        return reply.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }


    /**
     * 댓글 수정
     * */
    public void modify(Long commentId, String content) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        findComment.changeComment(content);
    }


    /**
     * 댓글 삭제
     * */
    public void deleteComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(findComment);
    }
}
