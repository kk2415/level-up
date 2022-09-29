package com.levelup.api.controller.v1.channel;

import com.levelup.api.controller.v1.dto.request.channel.ChannelRequest;
import com.levelup.channel.domain.service.dto.ChannelDto;
import com.levelup.api.controller.v1.dto.response.channel.ChannelStatInfoResponse;
import com.levelup.api.controller.v1.dto.response.channel.ChannelResponse;
import com.levelup.channel.domain.service.ChannelService;
import com.levelup.channel.domain.ChannelCategory;
import com.levelup.channel.domain.service.dto.ChannelStatInfoDto;
import com.levelup.common.util.file.UploadFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;

@Tag(name = "채널 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels")
public class ChannelApiController {

    private final ChannelService channelService;

    @PostMapping({"", "/"})
    public ResponseEntity<ChannelResponse> create(
            @Valid @RequestBody ChannelRequest request,
            @RequestParam("member") Long memberId)
    {
        ChannelDto dto = channelService.save(request.toDto(), memberId);

        return ResponseEntity.ok().body(ChannelResponse.from(dto));
    }

    @PostMapping({"/thumbnail", "/thumbnail/"})
    public ResponseEntity<UploadFile> createThumbnailImage(MultipartFile file) throws IOException {
        UploadFile response = channelService.createThumbnailImage(file);

        return ResponseEntity.ok().body(response);
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

    @GetMapping(path = {"/{channelId}/thumbnail", "/{channelId}/thumbnail/"}, produces = "image/jpeg")
    public Resource getThumbnailImage(@PathVariable Long channelId) throws MalformedURLException {
        return channelService.getThumbnailImage(channelId);
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
            @Valid @RequestBody ChannelRequest request)
    {
        channelService.update(request.toDto(), channelId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping({"/{channelId}/thumbnail", "/{channelId}/thumbnail/"})
    public ResponseEntity<UploadFile> updateThumbnailImage(
            @PathVariable Long channelId,
            MultipartFile file) throws IOException
    {
        UploadFile response = channelService.updateChannelThumbNail(file, channelId);

        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping({"/{channelId}", "/{channelId}/"})
    public ResponseEntity<Void> delete(
            @PathVariable Long channelId,
            @RequestParam("category") ChannelCategory category) {
        channelService.delete(channelId, category);

        return ResponseEntity.ok().build();
    }
}
