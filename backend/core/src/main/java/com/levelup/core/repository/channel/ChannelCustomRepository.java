package com.levelup.core.repository.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;

import java.util.List;

public interface ChannelCustomRepository {

    List<Channel> findByCategory(ChannelCategory category);
}
