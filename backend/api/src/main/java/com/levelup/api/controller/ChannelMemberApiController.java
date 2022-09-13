package com.levelup.api.controller;

import com.levelup.api.dto.service.chanelMember.ChannelMemberDto;
import com.levelup.api.service.ChannelMemberService;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.response.channelMember.ChannelMemberResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "채널 멤버 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChannelMemberApiController {

    private final ChannelMemberService channelMemberService;

    @PostMapping("channel-members")
    public ResponseEntity<ChannelMemberResponse> create(
            @RequestParam("channel") Long channelId,
            @RequestParam("member") Long memberId)
    {
        ChannelMemberDto dto = channelMemberService.create(channelId, memberId, true);

        return ResponseEntity.ok().body(ChannelMemberResponse.from(dto));
    }



    @GetMapping("/channel-members")
    public ResponseEntity<Page<ChannelMemberResponse>> getByPaging(
            @RequestParam Long channelId,
            @RequestParam Boolean isWaitingMember,
            Pageable pageable)
    {
        Page<ChannelMemberResponse> responses
                = channelMemberService.getByPaging(channelId, isWaitingMember, pageable)
                .map(ChannelMemberResponse::from);

        return ResponseEntity.ok().body(responses);
    }



    @PatchMapping("/channel-members/{channelMemberId}")
    public ResponseEntity<Void> approvalMember(@PathVariable Long channelMemberId) {
        channelMemberService.approvalMember(channelMemberId);

        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/channel-members/{channelMemberId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long channelMemberId,
            @RequestParam Long channelId)
    {
        channelMemberService.delete(channelMemberId, channelId);

        return ResponseEntity.ok().build();
    }
}
