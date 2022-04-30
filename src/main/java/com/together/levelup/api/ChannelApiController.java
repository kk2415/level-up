package com.together.levelup.api;

import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.ImageType;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelCategory;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.member.UploadFile;
import com.together.levelup.dto.*;
import com.together.levelup.dto.channel.*;
import com.together.levelup.exception.ImageNotFoundException;
import com.together.levelup.service.ChannelService;
import com.together.levelup.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelApiController {

    private final ChannelService channelService;
    private final MemberService memberService;
    private final FileStore fileStore;

    /**
     * 생성
     * */
    @PostMapping("/channel")
    public CreateChannelResponse create(@RequestBody @Validated ChannelRequest channelRequest) {
//        ArrayList<ChannelDescriptionFile> descriptionFiles = new ArrayList<>();

        Long channelId = channelService.create(channelRequest.getMemberEmail(), channelRequest.getName(),
                channelRequest.getLimitedMemberNumber(), channelRequest.getDescription(),
                channelRequest.getThumbnailDescription(),
                channelRequest.getCategory(), channelRequest.getThumbnailImage());

        Channel findChannel = channelService.findOne(channelId);

//        for (UploadFile file : uploadFiles) {
//            ChannelDescriptionFile descriptionFile = ChannelDescriptionFile.createChannelDescriptionFile(file);
//            descriptionFiles.add(descriptionFile);
//        }
//
//        Channel findChannel = channelService.findOne(channelId);
//        findChannel.addDescriptionFile(descriptionFiles);


        return new CreateChannelResponse(findChannel.getName(), findChannel.getLimitedMemberNumber(),
                findChannel.getManagerName(), findChannel.getDescription());
    }

    @PostMapping("/channel/descriptionFiles")
    public ResponseEntity storeDescriptionFiles(MultipartFile files) throws IOException {
        if (files == null || files.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        UploadFile uploadFiles = fileStore.storeFile(ImageType.CHANNEL, files);
        ChannelFileResponse channelFileResponse = new ChannelFileResponse(uploadFiles.getStoreFileName(),
                uploadFiles);

        return new ResponseEntity(channelFileResponse, HttpStatus.OK);
    }

    @PostMapping("/channel/thumbnail")
    public ResponseEntity storeChannelThumbnail(MultipartFile file) throws IOException {
        UploadFile uploadFile;

        if (file == null || file.isEmpty()) {
            uploadFile = new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_THUMBNAIL_IMAGE);
        }
        else {
            uploadFile = fileStore.storeFile(ImageType.CHANNEL_THUMBNAIL, file);
        }

        return new ResponseEntity(uploadFile, HttpStatus.OK);
    }


    /**
     * 조회
     * */
    @GetMapping("/channels")
    public Result channels() {
        List<Channel> channels = channelService.findAll();

        List<ChannelResponse> responseList = channels.stream().map(c -> new ChannelResponse(c.getId(),
                        c.getName(), c.getLimitedMemberNumber(), c.getManagerName(), c.getDescription(),
                        c.getThumbnailDescription(), c.getMemberCount())).collect(Collectors.toList());

        return new Result(responseList, responseList.size());
    }

    @GetMapping("/channel/{channelId}")
    public ChannelResponse channel(@PathVariable Long channelId) {
        Channel findChannel = channelService.findOne(channelId);

        return new ChannelResponse(findChannel.getId(), findChannel.getName(), findChannel.getLimitedMemberNumber(),
                findChannel.getManagerName(), findChannel.getDescription(),
                findChannel.getThumbnailDescription(), findChannel.getMemberCount());
    }

    @GetMapping("/detail-description/{channelId}")
    public ChannelDescriptionResponse channelDescription(@PathVariable Long channelId) {
        Channel findChannel = channelService.findOne(channelId);

        return new ChannelDescriptionResponse(findChannel.getName(), findChannel.getDescription(), findChannel.getThumbnailDescription(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findChannel.getDateCreated()),
                findChannel.getManagerName(), findChannel.getMemberCount(), findChannel.getLimitedMemberNumber(),
                findChannel.getCategory());
    }

    @GetMapping("/channels/{category}")
    public Result findByCategory(@PathVariable ChannelCategory category) {
        List<Channel> findChannels = channelService.findByCategory(category);

        List<ChannelResponse> responseList = findChannels.stream().map(c -> new ChannelResponse(c.getId(),
                        c.getName(), c.getLimitedMemberNumber(), c.getManagerName(),
                        c.getDescription(), c.getThumbnailDescription(), c.getMemberCount()))
                .collect(Collectors.toList());

        return new Result(responseList, responseList.size());
    }

    @GetMapping(path = "/channel/{id}/thumbnail", produces = "image/jpeg")
    public Resource findMemberProfile(@PathVariable Long id) throws MalformedURLException {
        Channel findChannel = channelService.findOne(id);

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


    /**
     * 수정
     * */
    @PatchMapping("/channel/{channelId}")
    public ResponseEntity updateDetailDescription(@PathVariable Long channelId, @RequestBody @Validated UpdateChannelRequest channelRequest) {
        channelService.update(channelId, channelRequest.getName(), channelRequest.getLimitedMemberNumber(),
                channelRequest.getDescription(), channelRequest.getThumbnailDescription(), channelRequest.getThumbnailImage());

        Channel findChannel = channelService.findOne(channelId);
        return new ResponseEntity(new UpdateChannelResponse(findChannel.getName(),
                findChannel.getLimitedMemberNumber(), findChannel.getDescription(),
                findChannel.getThumbnailDescription(), findChannel.getThumbnailImage()),
                HttpStatus.OK);
    }

}
