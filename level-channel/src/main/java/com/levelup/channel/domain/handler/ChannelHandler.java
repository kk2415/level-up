package com.levelup.channel.domain.handler;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.event.events.MemberDeletedEvent;
import com.levelup.event.events.MemberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Component
public class ChannelHandler {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;

    @CacheEvict(cacheNames = "channel", allEntries = true)
    @EventListener(MemberDeletedEvent.class)
    public void handleMemberDeletedEvent(MemberDeletedEvent event) {
        List<Channel> channels = channelMemberRepository.findByMemberId(event.getDeletedMemberId())
            .stream()
            .filter(ChannelMember::getIsManager)
            .map(ChannelMember::getChannel)
            .collect(Collectors.toUnmodifiableList());

        channelRepository.deleteAll(channels);
    }

    @EventListener(MemberUpdatedEvent.class)
    public void handleMemberUpdatedEvent(MemberUpdatedEvent event) {
        channelMemberRepository.findByMemberId(event.getMemberId())
                        .ifPresent(channelMember ->
                                channelMember.update(
                                        event.getEmail(),
                                        event.getNickname()));
    }
}