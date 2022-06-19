package com.levelup.api.controller;

import com.levelup.api.service.ChannelNoticeService;
import com.levelup.api.service.ChannelService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.channelNotice.ChannelNoticeRequest;
import com.levelup.core.dto.channelNotice.ChannelNoticeResponse;
import com.levelup.core.dto.channelNotice.DeleteChannelNoticeRequest;
import com.levelup.core.dto.channelNotice.PagingChannelNoticeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "채널 공지사항 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelNoticeApiController {

    private final ChannelNoticeService channelNoticeService;
    private final ChannelService channelService;


    /**
     * 생성
     * */
    @PostMapping("/channel-notice")
    public ResponseEntity<ChannelNoticeResponse> create(@RequestParam Long channel,
                                 @RequestBody @Validated ChannelNoticeRequest noticeRequest,
                                 @AuthenticationPrincipal Long memberId) {
        ChannelNoticeResponse notice = channelNoticeService.create(noticeRequest, channel, memberId);

        return ResponseEntity.ok().body(notice);
    }

    @PostMapping("/channel-notice/files")
    public ResponseEntity<UploadFile> createFile(MultipartFile file) throws IOException {
        UploadFile uploadFile = channelNoticeService.createFileByMultiPart(file);

        return ResponseEntity.ok().body(uploadFile);
    }


    /**
     * 조회
     * */
    @GetMapping("/channel-notice/{id}")
    public ResponseEntity getNotice(@PathVariable Long id,
                                    @RequestParam(required = false, defaultValue = "false") String view) {
        ChannelNoticeResponse channelNotice = channelNoticeService.getChannelNotice(id, view);

        return ResponseEntity.ok().body(channelNotice);
    }

    @GetMapping("/channel-notices")
    public ResponseEntity getNotices(@RequestParam Long channel,
                                     @RequestParam int page) {
        List<PagingChannelNoticeResponse> channelNotices = channelNoticeService.getChannelNotices(channel, page);

        return ResponseEntity.ok().body(new Result(channelNotices, channelNotices.size()));
    }

    @GetMapping("/channel-notice/{id}/nextPost")
    public ResponseEntity getNextPost(@PathVariable Long id) {
        ChannelNoticeResponse nextPage = channelNoticeService.findNextPage(id);

        return ResponseEntity.ok().body(nextPage);
    }

    @GetMapping("/channel-notice/{id}/prevPost")
    public ResponseEntity getPrevPost(@PathVariable Long id) {
        ChannelNoticeResponse prevPage = channelNoticeService.findPrevPage(id);

        return ResponseEntity.ok().body(prevPage);
    }


    /**
     * 수정
     * */
    @PatchMapping("/channel-notice/{id}")
    public ResponseEntity modifyChannelNotice(@PathVariable Long id,
                                              @RequestParam Long channel,
                                              @RequestBody @Validated ChannelNoticeRequest noticeRequest,
                                              @AuthenticationPrincipal Long memberId) {
        ChannelNoticeResponse channelNotice = channelNoticeService.modifyChannelNotice(noticeRequest, id,
                channel, memberId);

        return ResponseEntity.ok().body(channelNotice);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel-notice")
    public ResponseEntity deleteAll(@RequestParam Long channel,
                                 @RequestBody @Validated DeleteChannelNoticeRequest noticeRequest,
                                 @AuthenticationPrincipal Long memberId) {
        Long managerId = channelService.getById(channel).getManagerId();

        if (memberId.equals(managerId)) {
            channelNoticeService.deleteAll(noticeRequest.getIds());
            return new ResponseEntity(new Result("삭제되었습니다", 0), HttpStatus.OK);
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/channel-notice/{channelNoticeId}")
    public ResponseEntity deleteChannelNotice(@PathVariable Long channelNoticeId,
                                              @RequestParam Long channel,
                                              @AuthenticationPrincipal Long memberId) {
        channelNoticeService.delete(channelNoticeId, channel, memberId);

        return new ResponseEntity(new Result("삭제되었습니다", 0), HttpStatus.OK);
    }
}
