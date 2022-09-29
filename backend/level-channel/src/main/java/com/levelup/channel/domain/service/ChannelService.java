package com.levelup.channel.domain.service;

import com.levelup.channel.domain.repository.channel.ChannelRepository;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.channel.exception.ChannelNotFountExcpetion;
import com.levelup.channel.exception.ThumbnailImageNotFoundException;
import com.levelup.channel.domain.service.dto.ChannelStatInfoDto;
import com.levelup.common.domain.FileType;
import com.levelup.common.util.file.FileStore;
import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.member.domain.entity.Role;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.exception.MemberNotFoundException;
import com.levelup.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelService {

    private final FileStore fileStore;
    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;

    @Caching(evict = {
            @CacheEvict(cacheNames = "channel", key = "{#dto.category + ':id'}"),
            @CacheEvict(cacheNames = "channel", key = "{#dto.category + ':memberCount'}")
    })
    public ChannelDto save(ChannelDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 계정입니다."));
        member.addRole(Role.of(RoleName.CHANNEL_MANAGER, member));

        ChannelMember channelMember = ChannelMember.of(member, true, false);
        Channel channel = dto.toEntity(channelMember);
        channelRepository.save(channel);

        return ChannelDto.from(channel);
    }

    public UploadFile createThumbnailImage(MultipartFile file) throws IOException {
        return fileStore.storeFile(FileType.CHANNEL_THUMBNAIL, file);
    }


    @Transactional(readOnly = true)
    public ChannelDto get(Long channelId) {
        final Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

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

    /*
     * @ResponseBody + byte[]또는, Resource 를 반환하는 경우 바이트 정보가 반환됩니다.
     * <img> 에서는 이 바이트 정보를 읽어서 이미지로 반환하게 됩니다.
     *
     * Resource 를 리턴할 때 ResourceHttpMessageConverter 가 해당 리소스의 바이트 정보를 응답 바디에 담아줍니다.
     * */
    @Transactional(readOnly = true)
    public UrlResource getThumbnailImage(Long channelId) throws MalformedURLException {
        final Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        if (findChannel.getThumbnail() == null) {
            throw new ThumbnailImageNotFoundException("썸네일 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findChannel.getThumbnail();
        String fullPath = fileStore.getFullPath(uploadFile.getStoreFileName());

        return new UrlResource("file:" + fullPath);
    }

    @Transactional(readOnly = true)
    public ChannelStatInfoDto getStatInfo(Long channelId, Long memberId) {
        final Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        return ChannelStatInfoDto.from(channel);
    }


    @CacheEvict(cacheNames = "channel", allEntries = true)
    public void update(ChannelDto dto, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        channel.updateChannel(dto.getName(), dto.getCategory(), dto.getLimitedMemberNumber(), dto.getDescription(), dto.getThumbnailImage());
    }

    @CacheEvict(cacheNames = "channel", allEntries = true)
    public UploadFile updateChannelThumbNail(MultipartFile file, Long channelId) throws IOException {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        fileStore.deleteFile(channel.getThumbnail().getStoreFileName());

        UploadFile thumbNail = fileStore.storeFile(FileType.CHANNEL_THUMBNAIL, file);
        channel.updateThumbnail(thumbNail);
        return thumbNail;
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "channel", key = "{#category + ':id'}"),
            @CacheEvict(cacheNames = "channel", key = "{#category + ':memberCount'}")
    })
    public void delete(Long channelId, ChannelCategory category) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        channelRepository.delete(channel);
    }
}
