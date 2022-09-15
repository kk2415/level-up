package com.levelup.core.repository.article;

import com.levelup.core.domain.article.Article;
import com.levelup.core.dto.ArticlePagingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @EntityGraph(attributePaths = {"member", "comments"})
    Optional<Article> findById(Long id);

    @Query(value =
            "select a.article_id as articleId, " +
                    "a.title, " +
                    "a.views, " +
                    "a.article_type as articleType, " +
                    "a.created_at as createdAt, " +
                    "m.nickname as writer, " +
                    "(select count(1) from comment c where exists " +
                    "(select 1 from article where c.article_id = a.article_id)) as commentCount, " +
                    "(select count(1) from article_vote av where exists " +
                    "(select 1 from article where av.article_id = a.article_id)) as voteCount " +
                    "from article a left outer join member m on a.member_id = m.member_id " +
                    "where article_type = :articleType",
            countQuery = "select count from article_count ac where ac.article_type = :articleType",
            nativeQuery = true)
    Page<ArticlePagingDto> findByArticleType(@Param("articleType") String articleType, Pageable pageable);

    @Query(value =
            "select a.article_id as articleId, " +
                    "a.title, " +
                    "a.views, " +
                    "a.article_type as articleType, " +
                    "a.created_at as createdAt, " +
                    "m.nickname as writer, " +
                    "(select count(1) from comment c where exists " +
                    "(select 1 from article where c.article_id = a.article_id)) as commentCount, " +
                    "(select count(1) from article_vote av where exists " +
                    "(select 1 from article where av.article_id = a.article_id)) as voteCount " +
                    "from article a left outer join member m on a.member_id = m.member_id " +
                    "where a.article_type = :articleType and match(a.title) against(:title in boolean mode)",
            countQuery = "select count(*) from article a where a.article_type = :articleType and match(a.title) against(:title in boolean mode)",
            nativeQuery = true)
    Page<ArticlePagingDto> findByTitleAndArticleType(@Param("title") String title,
                                                     @Param("articleType") String articleType,
                                                     Pageable pageable);

    @Query(value =
            "select a.article_id as articleId, " +
                    "a.title, " +
                    "a.views, " +
                    "a.article_type as articleType, " +
                    "a.created_at as createdAt, " +
                    "m.nickname as writer, " +
                    "(select count(1) from comment c where exists " +
                    "(select 1 from article where c.article_id = a.article_id)) as commentCount, " +
                    "(select count(1) from article_vote av where exists " +
                    "(select 1 from article where av.article_id = a.article_id)) as voteCount " +
                    "from article a left outer join member m on a.member_id = m.member_id " +
                    "where a.article_type = :articleType and m.nickname like %:nickname%",
            countQuery = "select count(*) from article a join member m on m.member_id = a.member_id " +
                    "where a.article_type = :articleType and m.nickname like %:nickname%",
            nativeQuery = true)
    Page<ArticlePagingDto> findByNicknameAndArticleType(@Param("nickname") String nickname,
                                                        @Param("articleType") String articleType,
                                                        Pageable pageable);

    @Query(value = "select a.* from article a " +
            "where a.article_type = :articleType and a.article_id > :articleId " +
            "order by a.article_id limit 1",
            nativeQuery = true)
    Optional<Article> findNextByIdAndArticleType(@Param("articleId") Long articleId, @Param("articleType") String articleType);

    @Query(value = "select a.* from article a " +
            "where a.article_type = :articleType and a.article_id < :articleId " +
            "order by a.article_id desc limit 1",
            nativeQuery = true)
    Optional<Article> findPrevByIdAndArticleType(@Param("articleId") Long articleId, @Param("articleType") String articleType);
}
