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
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Tag(name = "채널 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChannelApiController {

    private final ChannelService channelService;
    private final MemberService memberService;


    /**
     * 생성
     * */
    @PostMapping("/channel")
    public CreateChannelResponse create(@RequestBody @Validated ChannelRequest channelRequest) {
        return channelService.save(channelRequest);
    }

    @PostMapping("/channel/description/files/base64")
    public ResponseEntity<Void> storeDescriptionFilesByBase64(@RequestBody Base64Dto base64) throws IOException {
        channelService.createFileByBase64(base64);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/channel/thumbnail")
    public ResponseEntity<UploadFile> createChannelThumbnail(MultipartFile file) throws IOException {
        UploadFile response = channelService.createChannelThumbnail(file);

        return ResponseEntity.ok().body(response);
    }


    /**
     * 조회
     * */
    @GetMapping("/channels")
    public ResponseEntity<Result> getAll() {
        List<ChannelResponse> response = channelService.getAll();

        return ResponseEntity.ok().body(new Result(response, response.size()));
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<ChannelResponse> getByChannelId(@PathVariable Long channelId) {
        ChannelResponse response = channelService.getChannel(channelId);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/channels/{category}")
    public ResponseEntity<List<ChannelResponse>> getByCategory(@PathVariable ChannelCategory category) {
        List<ChannelResponse> response = channelService.getByCategory(category);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/channel/{channelId}/thumbnail", produces = "image/jpeg")
    public Resource findMemberProfile(@PathVariable Long channelId) throws MalformedURLException {
        return channelService.getThumbNailImage(channelId);
    }

    @Operation(description = "채널 전체 정보(가입 회원, 게시글 등) 조회")
    @GetMapping("/channel/{channelId}/manager")
    public ResponseEntity<ChannelInfo> getChannelAllInfo(@PathVariable Long channelId,
                                   @AuthenticationPrincipal Member member) {
        ChannelInfo response = channelService.getChannelAllInfo(channelId, member.getId());

        return ResponseEntity.ok().body(response);
    }


    /**
     * 수정
     * */
    @PatchMapping("/channel/{channelId}")
    public ResponseEntity<ChannelResponse> modifyDetailDescription(@PathVariable Long channelId,
                                                  @RequestBody @Validated UpdateChannelRequest channelRequest) {
        ChannelResponse response = channelService.modify(
                channelId, channelRequest.getName(), channelRequest.getLimitedMemberNumber(),
                channelRequest.getDescription(), channelRequest.getThumbnailDescription(),
                channelRequest.getThumbnailImage());

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/channel/{channelId}/thumbnail")
    public ResponseEntity<UploadFile> modifyChannelThumbnail(@PathVariable Long channelId,
                                                 MultipartFile file) throws IOException {
        UploadFile response = channelService.modifyChannelThumbNail(file, channelId);

        return ResponseEntity.ok().body(response);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long channelId) {
        channelService.deleteChannel(channelId);

        return ResponseEntity.ok().build();
    }

}
