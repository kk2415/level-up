package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.exception.NoPlaceChannelException;
import com.levelup.channel.domain.service.dto.ChannelMemberDto;
import com.levelup.common.exception.EntityDuplicationException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelMemberService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public ChannelMemberDto create(Long channelId, ChannelMemberDto dto)
    {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));
        final Optional<ChannelMember> channelMembers
                = channelMemberRepository.findByChannelIdAndMemberId(channelId, dto.getMemberId());

        validateDuplicationAndMemberMaxNumber(channel, channelMembers);

        ChannelMember channelMember = ChannelMember.of(
                null,
                dto.getMemberId(),
                dto.getEmail(),
                dto.getNickname(),
                dto.isManager(),
                dto.isWaitingMember());
        channel.addChannelMember(channelMember);

        return ChannelMemberDto.from(channelMember);
    }

    private void validateDuplicationAndMemberMaxNumber(Channel channel, Optional<ChannelMember> channelMember) {
        if (channelMember.isPresent()) {
            throw new EntityDuplicationException(ErrorCode.CHANNEL_MEMBER_DUPLICATION);
        }

        if (channel.getChannelMembers().size() >= channel.getMemberMaxNumber() ) {
            throw new NoPlaceChannelException(ErrorCode.NO_PLACE_CHANNEL);
        }
    }


    public Page<ChannelMemberDto> getChannelMembers(Long channelId, Boolean isWaitingMember, Pageable pageable) {
        final Page<ChannelMember> channelMembers = channelMemberRepository.findByChannelIdAndIsWaitingMember(channelId, isWaitingMember, pageable);

        return channelMembers.map(ChannelMemberDto::from);
    }


    public void approvalMember(Long channelMemberId) {
        ChannelMember channelMember = channelMemberRepository.findById(channelMemberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        channelMember.setIsWaitingMember(false);
    }


    public void delete(Long channelMemberId, Long channelId) {
        ChannelMember channelMember = channelMemberRepository.findById(channelMemberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));
        channel.removeMembers(List.of(channelMember));

        channelMemberRepository.delete(channelMember);
    }
}
