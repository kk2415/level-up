package com.levelup.core.repository.article.ChannelPost;

import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelPostRepository extends JpaRepository<ChannelPost, Long>, ChannelPostQueryRepository {

    @Query("SELECT cp FROM ChannelPost cp " +
            "join fetch cp.member m " +
            "where cp.member.id = m.id and cp.id = :articleId")
    Optional<ChannelPost> findByArticleId(@Param("articleId") Long articleId);

    List<ChannelPost> findByChannelId(Long channelId);
    List<ChannelPost> findByChannelIdAndArticleType(Long channelId, ArticleType articleType);
    Page<ChannelPost> findByChannelId(Long channelId, Pageable pageable);

    @Query(value = "select cp from ChannelPost cp " +
            "join fetch cp.member m " +
            "where cp.channel.id = :channelId and cp.articleType = :articleType",
    countQuery = "select count(cp) from ChannelPost cp where cp.channel.id = :channelId and cp.articleType = :articleType")
    Page<ChannelPost> findByChannelIdAndArticleType(@Param("channelId") Long channelId,
                                                    @Param("articleType") ArticleType articleType,
                                                    Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.articleType = :articleType " +
            "and cp.title like %:title%")
    Page<ChannelPost> findByChannelIdAndTitleAndArticleType(@Param("channelId") Long channelId,
                                                            @Param("articleType") ArticleType articleType,
                                                            @Param("title") String title,
                                                            Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.articleType = :articleType " +
            "and cp.member.nickname like %:writer%")
    Page<ChannelPost> findByChannelIdAndWriterAndArticleType(@Param("channelId") Long channelId,
                                                             @Param("articleType") ArticleType articleType,
                                                             @Param("writer") String writer,
                                                             Pageable pageable);

}
