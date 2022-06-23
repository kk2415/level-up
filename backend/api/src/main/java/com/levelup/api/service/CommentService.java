package com.levelup.api.service;


import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.comment.CommentResponse;
import com.levelup.core.dto.comment.CreateCommentRequest;
import com.levelup.core.dto.comment.CreateReplyCommentRequest;
import com.levelup.core.exception.PostNotFoundException;
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
    public CommentResponse create(CreateCommentRequest commentRequest, Long memeberId) {
        Member findMember = memberRepository.findById(memeberId);
        Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다.0"));
        Comment findComment = commentRequest.toEntity(findMember, article);

        commentRepository.save(findComment);
        article.addCommentCount();

        return new CommentResponse(findComment);
    }

    public CommentResponse createReplyComment(CreateReplyCommentRequest commentRequest, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Comment parent = commentRepository.findById(commentRequest.getParentId());
        Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다.0"));
        Comment child = commentRequest.toEntity(member, article);

        commentRepository.save(child);
        parent.addChildComment(child);

        return new CommentResponse(child);
    }


    /**
     * 댓글 조회
     * */
    public List<CommentResponse> getComments(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        return comments.stream()
                .filter(c -> c.getParent() == null)
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> findReplyById(Long commentId) {
        List<Comment> reply = commentRepository.findReplyById(commentId);

        return reply.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
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
