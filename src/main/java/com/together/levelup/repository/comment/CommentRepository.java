package com.together.levelup.repository.comment;

import com.together.levelup.domain.comment.Comment;

import java.util.List;

public interface CommentRepository {

    public void save(Comment comment);
    public Comment findById(Long id);
    public List<Comment> findByMemberId(Long memberId);
    public List<Comment> findByPostId(Long postId);
    public List<Comment> findByNoticeId(Long noticeId);
    public List<Comment> findByChannelNoticeId(Long channelNoticeId);
    public List<Comment> findByQnaId(Long qnaId);
    public List<Comment> findAll();
    public void delete(Long id);
    public Long countAll();

}
