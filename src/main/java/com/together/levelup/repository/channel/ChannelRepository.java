package com.together.levelup.repository.channel;

import com.together.levelup.domain.channel.Channel;
import java.util.List;

public interface ChannelRepository {

    public void save(Channel channel);
    public Channel findById(Long id);
    public List<Channel> findByMemberId(Long memberId);
    public List<Channel> findByName(String name);
    public List<Channel> findAll();
    public List<Channel> findAll(int start, int end);
    public void delete(Long id);

}
