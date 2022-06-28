package com.levelup.api.service;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channel.ChannelInfo;
import com.levelup.core.domain.channel.ChannelMember;
import com.levelup.core.domain.file.S3FileStore;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.dto.file.Base64Dto;
import com.levelup.core.domain.file.LocalFileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.channel.*;
import com.levelup.core.exception.DuplicateChannelMemberException;
import com.levelup.core.exception.ImageNotFoundException;
import com.levelup.core.repository.channel.ChannelMemberRepository;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final MemberRepository memberRepository;
//    private final LocalFileStore fileStore;
    private final S3FileStore fileStore;

    @Value("${file.linux_local_dir}")
    private String fileDir;

    /**
     * 생성
     * */
    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public CreateChannelResponse create(ChannelRequest channelRequest) {
//        validationDuplicateChannel(channelRequest.getName());

        Member member = memberRepository.findByEmail(channelRequest.getMemberEmail());

        Channel channel = channelRequest.toEntity();
        channel.setManager(member);
        member.setAuthority(Authority.CHANNEL_MANAGER);

        channelRepository.save(channel);

        return new CreateChannelResponse(channel);
    }

    private void validationDuplicateChannel(String name) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        List<Channel> findChannels = channelRepository.findByName(name);

        if (findChannels.size() > 0) {
            throw new IllegalStateException("이미 존재하는 채널입니다.");
        }
    }

    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public void addMember(Long channelId, Long... memberIds) {
        Channel channel = channelRepository.findById(channelId);
        List<ChannelMember> channelMembers = new ArrayList<>();

        for (Long id : memberIds) {
            Member findMember = memberRepository.findById(id);
            ChannelMember channelMember = ChannelMember.createChannelMember(findMember);
            channelMembers.add(channelMember);
        }

        channel.addMember(channelMembers);
    }

    public void addWaitingMember(Long channelId, Long... memberIds) {
        Channel channel = channelRepository.findById(channelId);
        List<ChannelMember> channelMembers = new ArrayList<>();

        for (Long id : memberIds) {
            Member findMember = memberRepository.findById(id);
            List<ChannelMember> waitingMembers = channelMemberRepository.findByChannelAndWaitingMember(channelId, findMember.getId());

            if (waitingMembers.isEmpty()) {
                ChannelMember channelMember = ChannelMember.createChannelWaitingMember(findMember);
                channelMembers.add(channelMember);
            }
            else {
                throw new DuplicateChannelMemberException("이미 신청하셨습니다.");
            }
        }
        channel.addWaitingMember(channelMembers);
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


    /**
     * 채널 조회
     * */
    public ChannelResponse getById(Long channelId) {
        Channel findChannel = channelRepository.findById(channelId);

        return new ChannelResponse(findChannel);
    }

    public List<Channel> getByMemberId(Long memberId) {
        return channelRepository.findByMemberId(memberId);
    }

    @Cacheable(cacheNames = "ChannelCategory")
    public List<ChannelResponse> getByCategory(ChannelCategory category) {
        return channelRepository.findByCategory(category)
                .stream().map(ChannelResponse::new)
                .collect(Collectors.toList());
    }

    public List<ChannelResponse> getAll() {
        return channelRepository.findAll().stream()
                .map(ChannelResponse::new)
                .collect(Collectors.toList());
    }

    public List<Channel> getAll(int start, int end) {
        return channelRepository.findAll(start, end);
    }

        public UrlResource getThumbNailImage(Long channelId) throws MalformedURLException {
        Channel findChannel = channelRepository.findById(channelId);

        if (findChannel.getThumbnailImage() == null) {
            throw new ImageNotFoundException("썸네일 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findChannel.getThumbnailImage();
        String fullPath = fileStore.getFullPath(uploadFile.getStoreFileName());

        /*
         * @ResponseBody + byte[]또는, Resource를 반환하는 경우 바이트 정보가 반환됩니다.
         * <img> 에서는 이 바이트 정보를 읽어서 이미지로 반환하게 됩니다.
         *
         * Resource를 리턴할 때 ResourceHttpMessageConverter가 해당 리소스의 바이트 정보를 응답 바디에 담아줍니다.
         * */
        return new UrlResource("file:" + fullPath);
    }

    public ChannelInfo getChannelAllInfo(Long channelId, Long memberId) {
        Channel channel = channelRepository.findById(channelId);
        Member findMember = memberRepository.findById(memberId);

        ChannelInfo channelInfo = new ChannelInfo(channel.getName(), findMember.getName(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channel.getCreateAt()),
                channel.getMemberCount(), channel.getWaitingMemberCount(), channel.getPostCount(),
                channel.getThumbnailImage().getStoreFileName());

        return channelInfo;
    }


    /**
     * 채널 수정
     * */
    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public ChannelResponse update(Long channelId, String name, Long limitNumber, String description, String thumbnailDescription, UploadFile thumbnailImage) {
        Channel channel = channelRepository.findById(channelId);
        channel.modifyChannel(name, limitNumber, description, thumbnailDescription, thumbnailImage);

        return new ChannelResponse(channel);
    }

    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public UploadFile modifyChannelThumbNail(MultipartFile file, Long channelId) throws IOException {
        Channel channel = channelRepository.findById(channelId);

        deleteS3ThumbNail(channel.getThumbnailImage().getStoreFileName());

        UploadFile thumbNail = fileStore.storeFile(ImageType.CHANNEL_THUMBNAIL, file);
        channel.modifyThumbNail(thumbNail);
        return thumbNail;
    }


    /**
     * 채널 삭제
     * */
    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public void deleteChannel(Long channelId) {
        channelRepository.delete(channelId);
    }

    public void deleteChannelMember(Long channelId, Long memberId) {
//        ChannelMember channelMember = channelMemberRepository.findByChannelAndMember(channelId, memberId)

//        Channel findChannel = channelRepository.findById(channelId);
//        Member findMember = memberRepository.findById(memberId);

//        findChannel.getChannelMembers().remove(channelMember);
//        findMember.getChannelMembers().remove(channelMember);
//
//        channelMemberRepository.delete(channelMember.getId());
    }

    @CacheEvict(cacheNames = "ChannelCategory", allEntries = true)
    public void deleteMember(Long channelId, String email) {
        Channel findChannel = channelRepository.findById(channelId);
        Member findMember = memberRepository.findByEmail(email);

        List<ChannelMember> members = channelMemberRepository.findByChannelAndMember(channelId, findMember.getId());
        findChannel.removeMember(members);

        for (ChannelMember channelMember : members) {
            channelMemberRepository.delete(channelMember.getId());
        }
    }

    public void deleteWaitingMember(Long channelId, String email) {
        Channel findChannel = channelRepository.findById(channelId);
        Member findMember = memberRepository.findByEmail(email);

        List<ChannelMember> waitingMember = channelMemberRepository
                .findByChannelAndWaitingMember(channelId, findMember.getId());
        findChannel.removeWaitingMember(waitingMember);

        for (ChannelMember channelMember : waitingMember) {
            channelMemberRepository.delete(channelMember.getId());
        }
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
