package com.levelup.api.controller.v1.channel;

import com.levelup.api.adapter.client.MemberClient;
import com.levelup.api.controller.v1.dto.request.channel.ChannelRequest;
import com.levelup.api.controller.v1.dto.response.member.MemberResponse;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.api.controller.v1.dto.response.channel.ChannelStatInfoResponse;
import com.levelup.api.controller.v1.dto.response.channel.ChannelResponse;
import com.levelup.channel.domain.service.ChannelService;
import com.levelup.channel.domain.entity.ChannelCategory;
import com.levelup.channel.domain.service.dto.ChannelStatInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Tag(name = "채널 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels")
@RestController
public class ChannelApiController {

    private final MemberClient memberClient;
    private final ChannelService channelService;

    @PostMapping({"", "/"})
    public ResponseEntity<ChannelResponse> create(
            @RequestPart(value = "request") @Valid ChannelRequest request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam("member") Long memberId) throws IOException
    {
        MemberResponse member = memberClient.get(memberId);

        ChannelDto dto = channelService.save(
                request.toDto(memberId, member.getNickname()),
                thumbnail,
                member.getEmail(),
                member.getUploadFile().getStoreFileName());

        return ResponseEntity.ok().body(ChannelResponse.from(dto));
    }


    @GetMapping({"/{channelId}", "/{channelId}/"})
    public ResponseEntity<ChannelResponse> get(
            @PathVariable Long channelId)
    {
        ChannelDto dto = channelService.get(channelId);

        return ResponseEntity.ok().body(ChannelResponse.from(dto));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<ChannelResponse>> getChannels(
            @RequestParam ChannelCategory category,
            @RequestParam(defaultValue = "id") String order,
            Pageable pageable)
    {
        Page<ChannelResponse> response
                = channelService.getChannels(category, order, pageable).map(ChannelResponse::from);

        return ResponseEntity.ok().body(response);
    }


    @Operation(description = "채널 전체 정보(가입 회원, 게시글 등) 조회")
    @GetMapping({"/{channelId}/manager", "/{channelId}/manager/"})
    public ResponseEntity<ChannelStatInfoResponse> getStatInfo(
            @PathVariable("channelId") Long channelId,
            @RequestParam("member") Long memberId)
    {
        ChannelStatInfoDto dto = channelService.getStatInfo(channelId, memberId);

        return ResponseEntity.ok().body(ChannelStatInfoResponse.from(dto));
    }


    @PatchMapping({"/{channelId}", "/{channelId}/"})
    public ResponseEntity<Void> update(
            @PathVariable Long channelId,
            @RequestPart(value = "request") @Valid ChannelRequest request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException
    {
        channelService.update(request.toDto(), thumbnail, channelId);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping({"/{channelId}", "/{channelId}/"})
    public ResponseEntity<Void> delete(
            @PathVariable Long channelId,
            @RequestParam("category") ChannelCategory category)
    {
        channelService.delete(channelId, category);

        return ResponseEntity.ok().build();
    }
}
