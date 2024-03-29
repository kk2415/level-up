package com.levelup.channel.domain.repository.channel;

import com.levelup.channel.domain.constant.ChannelCategory;
import com.levelup.channel.domain.entity.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query("select ch from Channel ch " +
            "join fetch ch.channelMembers chm " +
            "where ch.id = :id")
    Optional<Channel> findById(@Param("id") Long id);

    @Query("select c from Channel c where c.category = :category order by c.id asc")
    Page<Channel> findByCategoryOrderByIdAsc(@Param("category") ChannelCategory category, Pageable pageable);

    @Query("select c from Channel c join c.channelMembers cm where c.category = :category group by c.id order by count(c.id) desc")
    Page<Channel> findByCategoryOrderByMemberCountDesc(@Param("category") ChannelCategory category, Pageable pageable);

    @Query(value =
            "select c.*, " +
            "(select count(1) from channel_member cm_2 " +
                "where exists (select 1 from channel c_2 where cm_2.channel_id = c.channel_id)) as memberCount " +
            "from channel c where channel_category = :category order by memberCount desc limit 10 offset 0",
            countQuery = "select count(*) from channel c where c.channel_category = :category",
            nativeQuery = true)
    List<Channel> findByCategoryAndOrderByMemberCount(@Param("category") String category);
}