package com.levelup.channel.domain.repository.channel;

import com.levelup.channel.domain.entity.ChannelMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    Optional<ChannelMember> findByMemberId(Long memberId);

    Optional<ChannelMember> findByChannelIdAndMemberId(Long channelId, Long memberId);

    @Query(value = "select cm from ChannelMember cm " +
            "join fetch cm.channel c " +
            "where cm.channel.id = :channelId and cm.isWaitingMember = :isWaitingMember",
            countQuery = "select count(cm) from ChannelMember cm " +
                    "where cm.channel.id = :channelId and cm.isWaitingMember = :isWaitingMember")
    Page<ChannelMember> findByChannelIdAndIsWaitingMember(@Param("channelId") Long channelId,
                                                          @Param("isWaitingMember") Boolean isWaitingMember,
                                                          Pageable pageable);
}
