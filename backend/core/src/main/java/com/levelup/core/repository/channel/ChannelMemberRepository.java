package com.levelup.core.repository.channel;


import com.levelup.core.domain.channel.ChannelMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    Optional<List<ChannelMember>> findByChannelIdAndMemberId(Long channelId, Long memberId);
    Optional<List<ChannelMember>> findByChannelIdAndWaitingMemberId(Long channelId, Long memberId);

    @Query(value = "select cm from ChannelMember cm " +
            "join fetch cm.channel c " +
            "join fetch cm.member m " +
            "where c.id = :channelId " +
            "and cm.isWaitingMember = :isWaitingMember",
    countQuery = "select count(cm) from ChannelMember cm " +
            "join cm.channel c " +
            "join cm.member m " +
            "where c.id = :channelId " +
            "and cm.isWaitingMember = :isWaitingMember")
    Page<ChannelMember> findByChannelIdAndIsWaitingMember(@Param("channelId") Long channelId,
                                                          @Param("isWaitingMember") Boolean isWaitingMember,
                                                          Pageable pageable);

    Optional<List<ChannelMember>> findByChannelIdAndIsManager(Long channelId, Boolean isManager);

}
