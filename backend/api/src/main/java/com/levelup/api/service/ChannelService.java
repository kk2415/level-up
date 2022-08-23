package com.levelup.api.service;

import com.levelup.api.dto.ChannelPagingResponse;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.role.Role;
import com.levelup.core.dto.channel.ChannelInfo;
import com.levelup.core.domain.channelMember.ChannelMember;
import com.levelup.api.util.S3FileStore;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.dto.file.Base64Dto;
import com.levelup.api.util.LocalFileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.channel.*;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final MemberRepository memberRepository;
    private final S3FileStore fileStore;

    @Value("${file.linux_local_dir}")
    private String fileDir;

    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public CreateChannelResponse save(ChannelRequest channelRequest) {
        Member member = memberRepository.findByEmail(channelRequest.getMemberEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));
        member.addRole(Role.of(RoleName.CHANNEL_MANAGER, member));

        Channel channel = channelRequest.toEntity(member.getNickname());
        channelRepository.save(channel);

        ChannelMember channelMember = ChannelMember.of(member, true, false);
        channel.setChannelMember(channelMember);

        return CreateChannelResponse.from(channel);
    }

    public void createFileByBase64(Base64Dto base64) throws IOException {
        File file = new File(fileDir + "/" + base64.getName()); //파일 생성 경로 수정해야됨
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        Base64.Decoder decoder = Base64.getDecoder();

        byte[] decodedBytes = decoder.decode(base64.getBase64().getBytes());
        fileOutputStream.write(decodedBytes);

        fileOutputStream.close();
    }

    public UploadFile createChannelThumbnail(MultipartFile file) throws IOException {
        return fileStore.storeFile(ImageType.CHANNEL_THUMBNAIL, file);
    }



    public ChannelResponse getChannel(Long channelId, Member member) {
        final Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));
        boolean isManager = getIsManager(findChannel, member);

        return ChannelResponse.of(findChannel, isManager);
    }

    private boolean getIsManager(Channel channel, Member member) {
        if (member != null) {
            Optional<ChannelMember> optionalMember = channel.getChannelMembers()
                    .stream()
                    .filter(ChannelMember::getIsManager)
                    .findFirst();

            if (optionalMember.isPresent()) {
                Member manager = optionalMember.get().getMember();
                return manager.getId().equals(member.getId());
            }
        }
        return false;
    }

    @Cacheable(cacheNames = "ChannelCategory")
    public Page<ChannelPagingResponse> getByCategory(ChannelCategory category, Pageable pageable) {
        return channelRepository.findByCategory(category.name(), pageable)
                .map(ChannelPagingResponse::from);
    }

    /*
     * @ResponseBody + byte[]또는, Resource 를 반환하는 경우 바이트 정보가 반환됩니다.
     * <img> 에서는 이 바이트 정보를 읽어서 이미지로 반환하게 됩니다.
     *
     * Resource 를 리턴할 때 ResourceHttpMessageConverter 가 해당 리소스의 바이트 정보를 응답 바디에 담아줍니다.
     * */
    public UrlResource getThumbNailImage(Long channelId) throws MalformedURLException {
        final Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        if (findChannel.getThumbnailImage() == null) {
            throw new ImageNotFoundException("썸네일 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findChannel.getThumbnailImage();
        String fullPath = fileStore.getFullPath(uploadFile.getStoreFileName());

        return new UrlResource("file:" + fullPath);
    }

    public ChannelInfo getChannelAllInfo(Long channelId, Long memberId) {
        final Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        return new ChannelInfo(channel);
    }



    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public ChannelResponse modify(Long channelId, String name, Long limitNumber, String description, String thumbnailDescription, UploadFile thumbnailImage) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFountExcpetion("채널이 존재하지 않습니다"));

        channel.modifyChannel(name, limitNumber, description, thumbnailDescription, thumbnailImage);

        return ChannelResponse.from(channel);
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

        findChannel.removeMember(channelMembers);
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
