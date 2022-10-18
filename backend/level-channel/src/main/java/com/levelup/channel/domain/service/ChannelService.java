package com.levelup.channel.domain.service;

import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.domain.service.dto.ChannelStatInfoDto;
import com.levelup.channel.exception.ChannelException;
import com.levelup.common.domain.FileType;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.common.util.file.FileStore;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.common.util.file.UploadFile;
import com.levelup.event.events.ChannelCreatedEvent;
import com.levelup.event.events.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ChannelService {

    private final FileStore fileStore;
    private final ChannelRepository channelRepository;

    @Caching(evict = {
            @CacheEvict(cacheNames = "channel", key = "{#dto.category + ':id'}"),
            @CacheEvict(cacheNames = "channel", key = "{#dto.category + ':memberCount'}")
    })
    public ChannelDto save(ChannelDto dto, MultipartFile file, String managerEmail, String managerProfileImage) throws IOException {
        UploadFile thumbnail = fileStore.storeFile(FileType.CHANNEL_THUMBNAIL, file);
        Channel channel = dto.toEntity(thumbnail);
        ChannelMember manager = ChannelMember.of(
                null,
                dto.getManagerId(),
                managerEmail,
                dto.getManagerNickname(),
                managerProfileImage,
                true,
                false);

        channel.addChannelMember(manager);
        channelRepository.save(channel);

        EventPublisher.raise(ChannelCreatedEvent.of(dto.getManagerId()));

        return ChannelDto.from(channel);
    }


    @Transactional(readOnly = true)
    public ChannelDto get(Long channelId) {
        final Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        return ChannelDto.from(findChannel);
    }

    @Cacheable(cacheNames = "channel", key = "{#category + ':' + #order}")
    @Transactional(readOnly = true)
    public Page<ChannelDto> getChannels(ChannelCategory category, String order, Pageable pageable) {
        if ("memberCount".equals(order)) {
            List<ChannelDto> collect = channelRepository.findByCategoryAndOrderByMemberCount(category.toString())
                    .stream().map(ChannelDto::from)
                    .collect(Collectors.toUnmodifiableList());

            return new PageImpl<ChannelDto>(collect);
        }
        return channelRepository.findByCategory(category, pageable)
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
    public void update(ChannelDto dto, MultipartFile file, Long channelId) throws IOException {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        UploadFile thumbnail = storeUpdateThumbnail(channel, file);
        channel.updateChannel(
                dto.getName(),
                dto.getCategory(),
                dto.getLimitedMemberNumber(),
                dto.getDescription(),
                thumbnail);
    }

    private UploadFile storeUpdateThumbnail(Channel channel, MultipartFile file) throws IOException {
        if (file == null) {
            return channel.getThumbnail();
        }

        fileStore.deleteFile(channel.getThumbnail().getStoreFileName());
        return fileStore.storeFile(FileType.CHANNEL_THUMBNAIL, file);
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "channel", key = "{#category + ':id'}"),
            @CacheEvict(cacheNames = "channel", key = "{#category + ':memberCount'}")
    })
    public void delete(Long channelId, ChannelCategory category) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_NOT_FOUND));

        channelRepository.delete(channel);
    }
}
