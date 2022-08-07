package com.levelup.core.repository.comment;


import com.levelup.core.domain.comment.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = "member")
    List<Comment> findReplyByParentId(Long parentId);

    @Query("select c from Comment c join fetch c.article a " +
            "join fetch c.member m " +
            "where a.id = :articleId order by c.id asc")
    List<Comment> findByArticleId(@Param("articleId") Long articleId);
}
