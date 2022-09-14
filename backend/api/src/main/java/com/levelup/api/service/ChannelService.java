package com.levelup.api.service;

import com.levelup.api.dto.service.channel.ChannelDto;
import com.levelup.api.dto.response.channel.ChannelStatInfoResponse;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.api.util.S3FileStore;
import com.levelup.core.domain.role.RoleName;
import com.levelup.api.util.LocalFileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.ImageNotFoundException;
import com.levelup.core.exception.channel.ChannelNotFountExcpetion;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.channelMember.ChannelMemberRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelService {

    @Value("${file.linux_local_dir}")
    private String fileDir;

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final MemberRepository memberRepository;
    private final S3FileStore fileStore;

    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
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
        return fileStore.storeFile(ImageType.CHANNEL_THUMBNAIL, file);
    }



    @Transactional(readOnly = true)
    public ChannelDto getChannel(Long channelId, Long memberId) {
        final Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        return ChannelDto.from(findChannel);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ChannelCategory", key = "{#category, #order}")
    public Page<ChannelDto> getByPaging(ChannelCategory category, String order, Pageable pageable) {
        if ("memberCount".equals(order)) {
            PageImpl<Channel> channelPage
                    = new PageImpl<>(channelRepository.findByCategoryAndOrderByMemberCount(category.toString()));
            return channelPage.map(ChannelDto::from);
        }

        return channelRepository.findByCategory(category, pageable).map(ChannelDto::from);
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



    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public void modify(ChannelDto dto, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        channel.modifyChannel(dto.getName(), dto.getCategory(), dto.getLimitedMemberNumber(), dto.getDescription(), dto.getThumbnailImage());
    }

    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public UploadFile modifyChannelThumbNail(MultipartFile file, Long channelId) throws IOException {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        deleteS3ThumbNail(channel.getThumbnailImage().getStoreFileName());

        UploadFile thumbNail = fileStore.storeFile(ImageType.CHANNEL_THUMBNAIL, file);
        channel.modifyThumbNail(thumbNail);
        return thumbNail;
    }



    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public void deleteChannel(Long channelId) {
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

    private void deleteLocalThumbNail(String storeFileName) {
        if (!storeFileName.equals(LocalFileStore.MEMBER_DEFAULT_IMAGE)) {
            File imageFile = new File(fileStore.getFullPath(storeFileName));
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

    private void deleteS3ThumbNail(String storeFileName) {
        if (!storeFileName.equals(S3FileStore.DEFAULT_IMAGE)) {
            fileStore.deleteS3File(storeFileName);
        }
    }
}
