package com.levelup.article.domain.repository;

import com.levelup.article.domain.entity.CommentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {

    @Query("select cv from CommentVote cv " +
            "where cv.memberId =:memberId and cv.comment.id =:commentId " +
            "order by cv.id desc")
    List<CommentVote> findByMemberIdAndCommentId(@Param("memberId") Long memberId, @Param("commentId") Long commentId);
}
