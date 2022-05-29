package com.levelup.core.repository.comment;


import com.levelup.core.domain.comment.Comment;

import java.util.List;

public interface CommentRepository {

    void save(Comment comment);
    Comment findById(Long id);
    List<Comment> findReplyById(Long id);
    List<Comment> findByMemberId(Long memberId);
    List<Comment> findByPostId(Long postId);
    List<Comment> findByNoticeId(Long noticeId);
    List<Comment> findByChannelNoticeId(Long channelNoticeId);
    List<Comment> findByQnaId(Long qnaId);
    List<Comment> findAll();
    void delete(Long id);
    Long countAll();

}
