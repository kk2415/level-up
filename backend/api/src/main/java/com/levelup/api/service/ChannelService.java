package com.levelup.api.service;

import com.levelup.api.service.dto.channel.ChannelDto;
import com.levelup.api.controller.v1.dto.response.channel.ChannelStatInfoResponse;
import com.levelup.api.util.file.FileStore;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.domain.file.FileType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.api.exception.ImageNotFoundException;
import com.levelup.api.exception.channel.ChannelNotFountExcpetion;
import com.levelup.api.exception.member.MemberNotFoundException;
import com.levelup.core.repository.channelMember.ChannelMemberRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    @CacheEvict(cacheNames = "channel", allEntries = true)
    public ChannelDto save(ChannelDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 계정입니다."));
        member.addRole(Role.of(RoleName.CHANNEL_MANAGER, member));

        Channel channel = dto.toEntity(member.getNickname());
        channelRepository.save(channel);

        ChannelMember channelMember = ChannelMember.of(member, true, false);
        channel.setChannelMember(channelMember);

        return ChannelDto.from(channel);
    }

    public UploadFile createThumbnailImage(MultipartFile file) throws IOException {
        return fileStore.storeFile(FileType.CHANNEL_THUMBNAIL, file);
    }



    @Transactional(readOnly = true)
    public ChannelDto get(Long channelId, Long memberId) {
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

        if (findChannel.getThumbnailImage() == null) {
            throw new ImageNotFoundException("썸네일 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findChannel.getThumbnailImage();
        String fullPath = fileStore.getFullPath(uploadFile.getStoreFileName());

        return new UrlResource("file:" + fullPath);
    }

    @Transactional(readOnly = true)
    public ChannelStatInfoResponse getStatInfo(Long channelId, Long memberId) {
        final Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        return new ChannelStatInfoResponse(channel);
    }



    @CacheEvict(cacheNames = "channel", allEntries = true)
    public void update(ChannelDto dto, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        channel.modifyChannel(dto.getName(), dto.getCategory(), dto.getLimitedMemberNumber(), dto.getDescription(), dto.getThumbnailImage());
    }

    @CacheEvict(cacheNames = "channel", allEntries = true)
    public UploadFile updateChannelThumbNail(MultipartFile file, Long channelId) throws IOException {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        fileStore.deleteFile(channel.getThumbnailImage().getStoreFileName());

        UploadFile thumbNail = fileStore.storeFile(FileType.CHANNEL_THUMBNAIL, file);
        channel.modifyThumbNail(thumbNail);
        return thumbNail;
    }



    @CacheEvict(cacheNames = "channel", allEntries = true)
    public void delete(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        channelRepository.delete(channel);
    }

    public void deleteMember(Long channelId, String email) {
        Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));
        List<ChannelMember> channelMembers = channelMemberRepository.findByChannelIdAndMemberId(channelId, findMember.getId());

        findChannel.removeMembers(channelMembers);
        channelMemberRepository.deleteAll(channelMembers);
    }
}
