package com.levelup.channel.domain.handler;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.exception.ChannelException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.event.events.ChannelCreatedEvent;
import com.levelup.event.events.MemberDeletedEvent;
import com.levelup.event.events.MemberProfileImageUpdatedEvent;
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
        ChannelMember channelMember = channelMemberRepository.findByMemberId(event.getMemberId())
                .orElseThrow(() -> new ChannelException(ErrorCode.CHANNEL_MEMBER_NOT_FOUND));

        channelMember.update(event.getEmail(), event.getNickname(), channelMember.getProfileImage());
    }

    @EventListener(MemberProfileImageUpdatedEvent.class)
    public void handleMemberProfileImageUpdatedEvent(MemberProfileImageUpdatedEvent event) {
        ChannelMember channelMember = channelMemberRepository.findByMemberId(event.getMemberId())
                .orElseThrow(() -> new ChannelException(ErrorCode.CHANNEL_MEMBER_NOT_FOUND));

        channelMember.update(channelMember.getEmail(), channelMember.getNickname(), event.getStoreFileName());
    }
}