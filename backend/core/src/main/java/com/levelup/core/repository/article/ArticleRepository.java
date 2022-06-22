package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleQueryRepository {

    Optional<List<Article>> findByMemberId(Long memberId);

    Optional<List<Article>> findByArticleIdAndArticleType(Long articleId, ArticleType articleType);

    @Query("select cp from ChannelPost cp where cp.articleId = :articleId")
    Optional<ChannelPost> findChannelPostById(@Param("articleId") Long articleId);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId order by cp.articleId desc")
    List<ChannelPost> findByChannelId(@Param("channelId") Long channelId);

//    @Query(value = "select cp from ChannelPost cp", countQuery = "select count(cp) from ChannelPost cp")
    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId")
    Page<ChannelPost> findByChannelId(@Param("channelId") Long channelId, Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.title like %:title%")
    Page<ChannelPost> findByChannelIdAndTitle(@Param("channelId") Long channelId,
                                              @Param("title") String title,
                                              Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.writer like %:writer%")
    Page<ChannelPost> findByChannelIdAndWriter(@Param("channelId") Long channelId,
                                              @Param("writer") String writer,
                                              Pageable pageable);
}
