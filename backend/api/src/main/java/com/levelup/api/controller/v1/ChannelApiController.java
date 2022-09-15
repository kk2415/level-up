package com.levelup.api.controller.v1;

import com.levelup.api.controller.v1.dto.request.channel.ChannelRequest;
import com.levelup.api.service.dto.channel.ChannelDto;
import com.levelup.api.controller.v1.dto.response.channel.ChannelStatInfoResponse;
import com.levelup.api.controller.v1.dto.response.channel.ChannelResponse;
import com.levelup.api.service.ChannelService;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.member.Member;
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
    public ResponseEntity<ChannelResponse> create(
            @RequestBody @Validated ChannelRequest request,
            @RequestParam("member") Long memberId)
    {
        ChannelDto dto = channelService.save(request.toDto(), memberId);

        return ResponseEntity.ok().body(ChannelResponse.from(dto));
    }

    @PostMapping("/channels/thumbnail")
    public ResponseEntity<UploadFile> createThumbnailImage(MultipartFile file) throws IOException {
        UploadFile response = channelService.createThumbnailImage(file);

        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/channels/{channelId}")
    public ResponseEntity<ChannelResponse> get(
            @PathVariable Long channelId,
            @AuthenticationPrincipal Member member)
    {
        ChannelDto dto = channelService.get(channelId, member == null ? null : member.getId());

        return ResponseEntity.ok().body(ChannelResponse.from(dto));
    }

    @GetMapping("/channels")
    public ResponseEntity<Page<ChannelResponse>> getChannels(
            @RequestParam ChannelCategory category,
            @RequestParam(defaultValue = "id") String order,
            Pageable pageable)
    {
        Page<ChannelResponse> response
                = channelService.getChannels(category, order, pageable).map(ChannelResponse::from);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/channels/{channelId}/thumbnail", produces = "image/jpeg")
    public Resource getThumbnailImage(@PathVariable Long channelId) throws MalformedURLException {
        return channelService.getThumbnailImage(channelId);
    }

    @Operation(description = "채널 전체 정보(가입 회원, 게시글 등) 조회")
    @GetMapping("/channels/{channelId}/manager")
    public ResponseEntity<ChannelStatInfoResponse> getStatInfo(
            @PathVariable("channelId") Long channelId,
            @RequestParam("member") Long memberId)
    {
        ChannelStatInfoResponse response = channelService.getStatInfo(channelId, memberId);

        return ResponseEntity.ok().body(response);
    }



    @PatchMapping("/channels/{channelId}")
    public ResponseEntity<Void> update(
            @PathVariable Long channelId,
            @RequestBody @Validated ChannelRequest request)
    {
        channelService.update(request.toDto(), channelId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/channels/{channelId}/thumbnail")
    public ResponseEntity<UploadFile> updateThumbnailImage(
            @PathVariable Long channelId,
            MultipartFile file) throws IOException
    {
        UploadFile response = channelService.updateChannelThumbNail(file, channelId);

        return ResponseEntity.ok().body(response);
    }



    @DeleteMapping("/channels/{channelId}")
    public ResponseEntity<Void> delete(@PathVariable Long channelId) {
        channelService.delete(channelId);

        return ResponseEntity.ok().build();
    }
}
