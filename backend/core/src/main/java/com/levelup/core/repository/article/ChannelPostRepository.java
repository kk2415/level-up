package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelPostRepository extends JpaRepository<ChannelPost, Long>, ChannelPostQueryRepository {

    Optional<ChannelPost> findByArticleId(Long articleId);

    List<ChannelPost> findByChannelId(Long channelId);
    List<ChannelPost> findByChannelIdAndArticleType(Long channelId, ArticleType articleType);
    Page<ChannelPost> findByChannelId(Long channelId, Pageable pageable);
    Page<ChannelPost> findByChannelIdAndArticleType(Long channelId, ArticleType articleType, Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.articleType = :articleType " +
            "and cp.title like %:title%")
    Page<ChannelPost> findByChannelIdAndTitleAndArticleType(@Param("channelId") Long channelId,
                                                            @Param("articleType") ArticleType articleType,
                                                            @Param("title") String title,
                                                            Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.articleType = :articleType " +
            "and cp.writer like %:writer%")
    Page<ChannelPost> findByChannelIdAndWriterAndArticleType(@Param("channelId") Long channelId,
                                                             @Param("articleType") ArticleType articleType,
                                                             @Param("writer") String writer,
                                                             Pageable pageable);

}
