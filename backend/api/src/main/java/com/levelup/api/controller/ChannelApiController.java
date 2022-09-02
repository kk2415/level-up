package com.levelup.api.controller;

import com.levelup.api.dto.channel.*;
import com.levelup.api.service.ChannelService;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.file.Base64Dto;
import com.levelup.core.domain.file.UploadFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Tag(name = "채널 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChannelApiController {

    private final ChannelService channelService;

    @PostMapping("/channels")
    public CreateChannelResponse create(@RequestBody @Validated ChannelRequest channelRequest) {
        return channelService.save(channelRequest);
    }

    @PostMapping("/channels/description/files/base64")
    public ResponseEntity<Void> storeDescriptionFilesByBase64(@RequestBody Base64Dto base64) throws IOException {
        channelService.createFileByBase64(base64);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/channels/thumbnail")
    public ResponseEntity<UploadFile> createChannelThumbnail(MultipartFile file) throws IOException {
        UploadFile response = channelService.createChannelThumbnail(file);

        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/channels")
    public ResponseEntity<Page<ChannelPagingResponse>> getByCategory(@RequestParam ChannelCategory category,
                                                                     Pageable pageable) {
        Page<ChannelPagingResponse> response = channelService.getByCategory(category, pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/channels/{channelId}")
    public ResponseEntity<ChannelResponse> getByChannelId(@PathVariable Long channelId,
                                                          @AuthenticationPrincipal Member member) {
        ChannelResponse response = channelService.getChannel(channelId, member == null ? null : member.getId());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/channels/{channelId}/thumbnail", produces = "image/jpeg")
    public Resource findMemberProfile(@PathVariable Long channelId) throws MalformedURLException {
        return channelService.getThumbNailImage(channelId);
    }

    @Operation(description = "채널 전체 정보(가입 회원, 게시글 등) 조회")
    @GetMapping("/channels/{channelId}/manager")
    public ResponseEntity<ChannelInfo> getChannelAllInfo(@PathVariable("channelId") Long channelId,
                                                         @RequestParam("member") Long memberId) {
        ChannelInfo response = channelService.getChannelAllInfo(channelId, memberId);

        return ResponseEntity.ok().body(response);
    }



    @PatchMapping("/channels/{channelId}")
    public ResponseEntity<ChannelResponse> modifyDetailDescription(@PathVariable Long channelId,
                                                  @RequestBody @Validated UpdateChannelRequest channelRequest) {
        ChannelResponse response = channelService.modify(
                channelId, channelRequest.getName(), channelRequest.getLimitedMemberNumber(),
                channelRequest.getDescription(), channelRequest.getThumbnailDescription(),
                channelRequest.getThumbnailImage());

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/channels/{channelId}/thumbnail")
    public ResponseEntity<UploadFile> modifyChannelThumbnail(@PathVariable Long channelId,
                                                 MultipartFile file) throws IOException {
        UploadFile response = channelService.modifyChannelThumbNail(file, channelId);

        return ResponseEntity.ok().body(response);
    }



    @DeleteMapping("/channels/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long channelId) {
        channelService.deleteChannel(channelId);

        return ResponseEntity.ok().build();
    }
}
