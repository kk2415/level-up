package com.together.levelup.repository.channel;

import com.together.levelup.domain.channel.ChannelMember;

import java.util.List;

public interface ChannelMemberRepository {

    public Long save(ChannelMember channelMember);
    public List<ChannelMember> findByChannelAndMember(Long channelId, Long memberId);
    public List<ChannelMember> findByChannelAndWaitingMember(Long channelId, Long memberId);
    public void delete(Long id);

}
