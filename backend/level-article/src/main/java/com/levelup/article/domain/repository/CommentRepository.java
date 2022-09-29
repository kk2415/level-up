package com.levelup.article.domain.repository;

import com.levelup.article.domain.entity.ArticleComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<ArticleComment, Long> {

    @EntityGraph(attributePaths = "member")
    List<ArticleComment> findReplyByParentId(Long parentId);

    @Query("select c from ArticleComment c " +
            "join fetch c.article a " +
            "join fetch c.member m " +
            "where a.id = :articleId order by c.id asc")
    List<ArticleComment> findByArticleId(@Param("articleId") Long articleId);
}
