package com.levelup.core.repository.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelCustomRepository {

    @Query("select ch from Channel ch " +
            "join fetch ch.channelMembers chm " +
            "join fetch chm.member m " +
            "join fetch m.emailAuth ea " +
            "where ch.id = :id")
    Optional<Channel> findById(@Param("id") Long id);

    @Query("select ch from Channel ch " +
            "join ChannelPost cp on ch.id = cp.channel.id " +
            "where cp.id = :articleId")
    Optional<Channel> findByArticleId(@Param("articleId") Long articleId);

    Page<Channel> findByCategory(@Param("category") ChannelCategory category, Pageable pageable);
}
