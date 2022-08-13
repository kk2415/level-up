package com.levelup.core.repository.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.dto.channel.ChannelPagingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

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

    @Query(value =
            "select " +
                    "c.channel_id as channelId, " +
                    "c.channel_name as channelName, " +
                    "c.member_max_number as memberMaxNumber, " +
                    "c.main_description as mainDescription, " +
                    "c.store_file_name as storeFileName, " +
                    "c.created_at as createdAt, " +
                    "c.manager_name as managerName, " +
                    "(select count(1) from channel_member cm_2 " +
                        "where exists (select 1 from channel c_2 where cm_2.channel_id = c.channel_id)) as memberCount " +
                    "from channel c where channel_category = :category",
            countQuery = "select count(*) from channel c where c.channel_category = :category",
            nativeQuery = true)
    Page<ChannelPagingDto> findByCategory(@Param("category") String category, Pageable pageable);

    @EntityGraph(attributePaths = {"channelMembers", "channelMembers.member"})
    Page<Channel> findJoinFetchByCategory(@Param("category") ChannelCategory category, Pageable pageable);
}