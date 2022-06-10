package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<List<Article>> findByCategory(ArticleCategory category);
    Optional<List<Article>> findByIdAndCategory(Long articleId, ArticleCategory category);

    @Query("select a from Article a inner join a.channel c " +
            "where c.id = :channelId")
    Optional<List<Article>> findByChannelId(@Param("channelId") Long channelId);


    @Query("select a from Article a inner join a.channel c " +
            "where c.id = :channelId")
    Optional<Page<Article>> findByChannelId(@Param("channelId") Long channelId, Pageable pageable);

}
