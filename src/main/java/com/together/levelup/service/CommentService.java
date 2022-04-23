package com.together.levelup.service;

import com.together.levelup.domain.Comment;
import com.together.levelup.domain.post.Post;
import com.together.levelup.domain.member.Member;
import com.together.levelup.repository.comment.CommentRepository;
import com.together.levelup.repository.member.MemberRepository;
import com.together.levelup.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 작성
     * */
    public Long comment(Long memeberId, Long postId, String content) {
        Member findMember = memberRepository.findById(memeberId);
        Post findPost = postRepository.findById(postId);

        Comment comment = Comment.createComment(findMember, findPost, content);
        commentRepository.save(comment);
        return comment.getId();
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

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

}
