package com.together.levelup.api;

import com.together.levelup.domain.FileStore;
import com.together.levelup.domain.ImageType;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.member.UploadFile;
import com.together.levelup.dto.*;
import com.together.levelup.exception.ImageNotFoundException;
import com.together.levelup.service.ChannelService;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelApiController {

    private final ChannelService channelService;
    private final FileStore fileStore;

    @GetMapping("/channels")
    public Result channels() {
        List<Channel> channels = channelService.findAll();

        List<ChannelResponse> responseList = channels.stream().map(c -> new ChannelResponse(c.getId(),
                                                    c.getName(), c.getLimitedMemberNumber(), c.getManagerName(),
                                                    c.getDescript(), c.getMemberCount()))
                                                .collect(Collectors.toList());

        return new Result(responseList, responseList.size());
    }

    @GetMapping("/channel/{channelId}")
    public ChannelResponse channels(@PathVariable Long channelId) {
        Channel findChannel = channelService.findOne(channelId);

        return new ChannelResponse(findChannel.getId(), findChannel.getName(), findChannel.getLimitedMemberNumber(),
                findChannel.getManagerName(), findChannel.getDescript(), findChannel.getMemberCount());
    }

    @PostMapping("/channel")
    public ChannelResponse create(@RequestBody @Validated ChannelRequest channelRequest) {
        Long channelId = channelService.create(channelRequest.getMemberEmail(), channelRequest.getName(),
                channelRequest.getLimitedMemberNumber(), channelRequest.getDescription(), channelRequest.getUploadFile());

        return new ChannelResponse(channelId, channelRequest.getName(), channelRequest.getLimitedMemberNumber(), channelRequest.getManagerName(), channelRequest.getDescription(), 0L);
    }

    @PostMapping("/channel/thumbnail")
    public ResponseEntity storeChannelThumbnail(MultipartFile file) throws IOException {
        UploadFile uploadFile;

        if (file == null || file.isEmpty()) {
            uploadFile = new UploadFile("default.png", FileStore.CHANNEL_DEFAULT_IMAGE);
        }
        else {
            uploadFile = fileStore.storeFile(ImageType.CHANNEL, file);
        }

        return new ResponseEntity(uploadFile, HttpStatus.OK);
    }

    @GetMapping(path = "/channel/{id}/thumbnail", produces = "image/jpeg")
    public Resource findMemberProfile(@PathVariable Long id) throws MalformedURLException {
        Channel findChannel = channelService.findOne(id);

        if (findChannel.getUploadFile() == null) {
            throw new ImageNotFoundException("썸네일 사진을 찾을 수 없습니다");
        }

        UploadFile uploadFile = findChannel.getUploadFile();
        String fullPath = fileStore.getFullPath(uploadFile.getStoreFileName());

        /*
         * @ResponseBody + byte[]또는, Resource를 반환하는 경우 바이트 정보가 반환됩니다.
         * <img> 에서는 이 바이트 정보를 읽어서 이미지로 반환하게 됩니다.
         *
         * Resource를 리턴할 때 ResourceHttpMessageConverter가 해당 리소스의 바이트 정보를 응답 바디에 담아줍니다.
         * */
        return new UrlResource("file:" + fullPath);
    }

}
