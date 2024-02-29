package com.levelup.api.controller.v1.channel;

import com.levelup.api.controller.v1.dto.request.channel.ChannelRequest;
import com.levelup.api.controller.v1.dto.request.channel.CreateChannelRequest;
import com.levelup.channel.domain.service.ChannelSort;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.api.controller.v1.dto.response.channel.ChannelStatInfoResponse;
import com.levelup.api.controller.v1.dto.response.channel.ChannelResponse;
import com.levelup.channel.domain.service.ChannelService;
import com.levelup.channel.domain.constant.ChannelCategory;
import com.levelup.channel.domain.service.dto.ChannelStatInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Tag(name = "채널 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels")
@RestController
public class ChannelApiController {

    private final ChannelService channelService;

    @PostMapping({"", "/"})
    public ResponseEntity<ChannelResponse> create(
            @RequestBody @Valid CreateChannelRequest request,
            @RequestParam("member") Long memberId
    ) throws IOException {
        ChannelDto dto = channelService.save(request.toDto(), memberId);

        return ResponseEntity.ok().body(ChannelResponse.from(dto));
    }

    @GetMapping({"/{channelId}", "/{channelId}/"})
    public ResponseEntity<ChannelResponse> get(
            @PathVariable Long channelId
    ) {
        ChannelDto dto = channelService.get(channelId);

        return ResponseEntity.ok().body(ChannelResponse.from(dto));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<ChannelResponse>> getChannels(
            @RequestParam ChannelCategory category,
            @RequestParam ChannelSort sort,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<ChannelResponse> response = channelService.getChannels(category, sort, PageRequest.of(page, size))
                .map(ChannelResponse::from);

        return ResponseEntity.ok().body(response);
    }

    @Operation(description = "채널 전체 정보(가입 회원, 게시글 등) 조회")
    @GetMapping({"/{channelId}/manager", "/{channelId}/manager/"})
    public ResponseEntity<ChannelStatInfoResponse> getStatInfo(
            @PathVariable("channelId") Long channelId,
            @RequestParam("member") Long memberId
    ) {
        ChannelStatInfoDto dto = channelService.getStatInfo(channelId, memberId);

        return ResponseEntity.ok().body(ChannelStatInfoResponse.from(dto));
    }

    @PatchMapping({"/{channelId}", "/{channelId}/"})
    public ResponseEntity<Void> update(
            @PathVariable Long channelId,
            @RequestBody @Valid ChannelRequest request
    ) throws IOException {
        channelService.update(request.toDto(), channelId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping({"/{channelId}", "/{channelId}/"})
    public ResponseEntity<Void> delete(
            @PathVariable Long channelId,
            @RequestParam("category") ChannelCategory category
    ) {
        channelService.delete(channelId, category);

        return ResponseEntity.ok().build();
    }
}
