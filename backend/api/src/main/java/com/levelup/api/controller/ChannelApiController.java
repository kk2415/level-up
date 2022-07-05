package com.levelup.api.controller;

import com.levelup.api.service.ChannelService;
import com.levelup.api.service.MemberService;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.channel.ChannelInfo;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.file.Base64Dto;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.channel.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Tag(name = "채널 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelApiController {

    private final ChannelService channelService;
    private final MemberService memberService;


    /**
     * 생성
     * */
    @PostMapping("/channel")
    public CreateChannelResponse create(@RequestBody @Validated ChannelRequest channelRequest) {
        return channelService.create(channelRequest);
    }

    @PostMapping("/channel/description/files/base64")
    public ResponseEntity storeDescriptionFilesByBase64(@RequestBody Base64Dto base64) throws IOException {
        channelService.createFileByBase64(base64);
        return ResponseEntity.ok().body("파일 생성 완료");
    }

    @PostMapping("/channel/thumbnail")
    public ResponseEntity createChannelThumbnail(MultipartFile file) throws IOException {
        UploadFile thumbnail = channelService.createChannelThumbnail(file);

        return ResponseEntity.ok().body(thumbnail);
    }


    /**
     * 조회
     * */
    @GetMapping("/channels")
    public Result getAll() {
        List<ChannelResponse> channels = channelService.getAll();

        return new Result(channels, channels.size());
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<ChannelResponse> getByChannelId(@PathVariable Long channelId) {
        ChannelResponse channelResponse = channelService.getChannel(channelId);

        return ResponseEntity.ok().body(channelResponse);
    }

    @GetMapping("/channels/{category}")
    public Result getByCategory(@PathVariable ChannelCategory category) {
        List<ChannelResponse> findChannels = channelService.getByCategory(category);

        return new Result(findChannels, findChannels.size());
    }

    @GetMapping(path = "/channel/{channelId}/thumbnail", produces = "image/jpeg")
    public Resource findMemberProfile(@PathVariable Long channelId) throws MalformedURLException {
        return channelService.getThumbNailImage(channelId);
    }

    @Operation(description = "채널 전체 정보(가입 회원, 게시글 등) 조회")
    @GetMapping("/channel/{channelId}/manager")
    public ResponseEntity<ChannelInfo> getChannelAllInfo(@PathVariable Long channelId,
                                   @AuthenticationPrincipal Member member) {
        ChannelInfo channelAllInfo = channelService.getChannelAllInfo(channelId, member.getId());

        return ResponseEntity.ok().body(channelAllInfo);
    }


    /**
     * 수정
     * */
    @PatchMapping("/channel/{channelId}")
    public ResponseEntity modifyDetailDescription(@PathVariable Long channelId,
                                                  @RequestBody @Validated UpdateChannelRequest channelRequest) {
        ChannelResponse findChannel = channelService.update(channelId, channelRequest.getName(), channelRequest.getLimitedMemberNumber(),
                channelRequest.getDescription(), channelRequest.getThumbnailDescription(), channelRequest.getThumbnailImage());

        return ResponseEntity.ok().body(findChannel);
    }

    @PatchMapping("/channel/{channelId}/thumbnail")
    public ResponseEntity modifyChannelThumbnail(@PathVariable Long channelId,
                                                 MultipartFile file) throws IOException {
        UploadFile thumbNail = channelService.modifyChannelThumbNail(file, channelId);

        return ResponseEntity.ok().body(thumbNail);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel/{channelId}")
    public ResponseEntity deleteChannel(@PathVariable Long channelId) {
        channelService.deleteChannel(channelId);

        return new ResponseEntity(new Result("삭제 완료", 1), HttpStatus.OK);
    }

}
