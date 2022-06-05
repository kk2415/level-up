package com.levelup.core.repository.channel;



import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;

import java.util.List;

public interface ChannelRepository {

    void save(Channel channel);
    Channel findById(Long id);
    List<Channel> findByMemberId(Long memberId);
    List<Channel> findByName(String name);
    List<Channel> findByCategory(ChannelCategory category);
    List<Channel> findAll();
    List<Channel> findAll(int start, int end);
    void delete(Long id);

}
