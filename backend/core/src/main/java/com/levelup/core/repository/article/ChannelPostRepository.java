package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.ChannelPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChannelPostRepository extends JpaRepository<ChannelPost, Long>, ChannelPostQueryRepository {

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId")
    Page<ChannelPost> findByChannelId(@Param("channelId") Long channelId, Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.title = :title")
    Page<ChannelPost> findByChannelIdAndTitle(@Param("channelId") Long channelId,
                                              @Param("title") String title,
                                              Pageable pageable);

    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.writer = :writer")
    Page<ChannelPost> findByChannelIdAndWriter(@Param("channelId") Long channelId,
                                               @Param("writer") String writer,
                                               Pageable pageable);

}
