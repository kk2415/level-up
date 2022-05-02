package com.together.levelup.api;

import com.together.levelup.dto.ChannelNoticeRequest;
import com.together.levelup.service.ChannelNoticeService;
import com.together.levelup.service.ChannelService;
import com.together.levelup.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelNoticeApiController {

    private final ChannelNoticeService channelNoticeService;
    private final ChannelService channelService;
    private final MemberService memberService;

    /**
     * 생성
     * */
    @PostMapping("/channel-notice")
    public ResponseEntity create(@RequestParam Long channel,
                                 @RequestParam Long member,
            @RequestBody @Validated ChannelNoticeRequest request) {

        Long noticeId = channelNoticeService.create(channel, member, request.getTitle(), request.getWriter(),
                request.getContent());

        return new ResponseEntity(HttpStatus.CREATED);
    }



}
