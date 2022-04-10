package com.together.community.repository.comment;

import com.together.community.domain.Comment;

import java.util.List;

public interface CommentRepository {

    public void save(Comment comment);
    public Comment findById(Long id);
    public List<Comment> findByMemberId(Long memberId);
    public List<Comment> findByPostId(Long postId);
    public List<Comment> findAll();
    public void delete(Long id);
    public Long countAll();

}
