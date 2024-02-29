package com.levelup.channel.domain.repository.vote;

import com.levelup.channel.domain.entity.ChannelCommentVote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChannelCommentVoteRepository extends JpaRepository<ChannelCommentVote, Long> {

    @EntityGraph(attributePaths = {"channelMember", "channelMember.channel", "comment"})
    List<ChannelCommentVote> findByChannelMemberIdAndCommentId(Long channelMemberId, Long commentId);
}
