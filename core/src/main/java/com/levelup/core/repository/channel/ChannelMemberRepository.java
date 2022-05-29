package com.levelup.core.repository.channel;


import com.levelup.core.domain.channel.ChannelMember;

import java.util.List;

public interface ChannelMemberRepository {

    Long save(ChannelMember channelMember);
    List<ChannelMember> findByChannelAndMember(Long channelId, Long memberId);
    List<ChannelMember> findByChannelAndWaitingMember(Long channelId, Long memberId);
    void delete(Long id);

}
