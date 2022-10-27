package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.domain.service.dto.ChannelStatInfoDto;
import com.levelup.channel.domain.service.dto.CreateChannelDto;
import com.levelup.channel.exception.ChannelException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.event.events.ChannelCreatedEvent;
import com.levelup.event.events.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional
@Service
public class ChannelService {

    private final ChannelRepository channelRepository;


    /**
     * 채널 생성 시 회원의 권한을 수정하기 위해 이벤트 발행함
     * */
    @Caching(evict = {
            @CacheEvict(cacheNames = "channel", key = "{#dto.category + ':ID'}"),
            @CacheEvict(cacheNames = "channel", key = "{#dto.category + ':MEMBER_COUNT'}")
    })
    public ChannelDto save(CreateChannelDto dto, Long memberId) throws IOException {
        Channel channel = dto.toEntity();
        ChannelMember manager = ChannelMember.of(
                null,
                memberId,
                dto.getManagerEmail(),
                dto.getManagerNickname(),
                true,
                false);

        channel.addChannelMember(manager);
        channelRepository.save(channel);

        EventPublisher.raise(ChannelCreatedEvent.of(memberId));

        return ChannelDto.from(channel);
    }


    @Transactional(readOnly = true)
    public ChannelDto get(Long channelId) {
        final Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        return ChannelDto.from(findChannel);
    }

    @Cacheable(cacheNames = "channel", key = "{#category + ':' + #sort}")
    @Transactional(readOnly = true)
    public Page<ChannelDto> getChannels(ChannelCategory category, ChannelSort sort, Pageable pageable) {
        if (ChannelSort.MEMBER_COUNT.equals(sort)) {
            return channelRepository.findByCategoryOrderByMemberCountDesc(category, pageable)
                    .map(ChannelDto::from);
        }

        return channelRepository.findByCategoryOrderByIdAsc(category, pageable)
                .map(ChannelDto::from);
    }

    @Transactional(readOnly = true)
    public ChannelStatInfoDto getStatInfo(Long channelId, Long memberId) {
        final Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        if (!channel.getManagerId().equals(memberId)) {
            throw new ChannelException(ErrorCode.CHANNEL_AUTHORITY_EXCEPTION);
        }

        return ChannelStatInfoDto.from(channel);
    }


    @CacheEvict(cacheNames = "channel", allEntries = true)
    public void update(ChannelDto dto, Long channelId) throws IOException {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        channel.updateChannel(
                dto.getName(),
                dto.getCategory(),
                dto.getLimitedMemberNumber(),
                dto.getDescription());
    }


    /*
    * 채널 삭제 시에는 채널에 가입된 채널 회원과, 게시글이 모두 삭제된다.
    * */
    @Caching(evict = {
            @CacheEvict(cacheNames = "channel", key = "{#category + ':ID'}"),
            @CacheEvict(cacheNames = "channel", key = "{#category + ':MEMBER_COUNT'}")
    })
    public void delete(Long channelId, ChannelCategory category) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        channelRepository.delete(channel);
    }
}
