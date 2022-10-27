package com.levelup.channel.domain.repository.article;

import com.levelup.channel.domain.entity.ChannelArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChannelArticleRepository extends JpaRepository<ChannelArticle, Long> {

    @EntityGraph(attributePaths = {"channelMember"})
    Optional<ChannelArticle> findById(@Param("articleId") Long articleId);

    @Query(value = "select ca from ChannelArticle ca where ca.channel.id = :channelId",
            countQuery = "select count(ca) from ChannelArticle ca where ca.channel.id = :channelId")
    Page<ChannelArticle> findByChannelId(@Param("channelId") Long channelId, Pageable pageable);

    @Query(value = "select ca from ChannelArticle ca where ca.channel.id = :channelId and match(ca.title, :title) > 0",
            countQuery = "select count(ca) from ChannelArticle ca where ca.channel.id = :channelId and match(ca.title, :title) > 0")
    Page<ChannelArticle> findByChannelIdAndTitle(@Param("channelId") Long channelId,
                                                 @Param("title") String title,
                                                 Pageable pageable);

    @Query(value = "select ca from ChannelArticle ca " +
            "join ca.channelMember cm " +
            "where ca.channel.id = :channelId and cm.nickname like %:nickname%",
            countQuery = "select count(ca) from ChannelArticle ca " +
                    "join ca.channelMember cm " +
                    "where ca.channel.id = :channelId and cm.nickname like %:nickname%")
    Page<ChannelArticle> findByChannelIdAndNickname(@Param("channelId") Long channelId,
                                                    @Param("nickname") String nickname,
                                                    Pageable pageable);

    @Query(value = "select ar.* from channel_article ar " +
            "join channel c on c.channel_id = ar.channel_id " +
            "where c.channel_id = :channelId and ar.channel_article_id > :articleId limit 1",
            nativeQuery = true)
    Optional<ChannelArticle> findNextByChannelId(@Param("articleId") Long articleId, @Param("channelId") Long channelId);

    @Query(value = "select ar.* from channel_article ar " +
            "join channel c on c.channel_id = ar.channel_id " +
            "where c.channel_id = :channelId and ar.channel_article_id < :articleId " +
            "order by ar.channel_article_id desc limit 1",
            nativeQuery = true)
    Optional<ChannelArticle> findPrevByChannelId(@Param("articleId") Long articleId, @Param("channelId") Long channelId);
}
