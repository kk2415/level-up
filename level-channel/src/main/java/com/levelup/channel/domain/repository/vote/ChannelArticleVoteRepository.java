package com.levelup.channel.domain.repository.vote;

import com.levelup.channel.domain.entity.ChannelArticleVote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelArticleVoteRepository extends JpaRepository<ChannelArticleVote, Long> {

    @EntityGraph(attributePaths = {"channelMember", "channelMember.channel", "article"})
    List<ChannelArticleVote> findByChannelMemberIdAndArticleId(Long channelMemberId, Long articleId);
}
