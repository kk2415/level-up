package com.levelup.api.controller.v1.channel;

import com.levelup.api.adapter.client.MemberClient;
import com.levelup.api.controller.v1.dto.request.channel.CreateMemberRequest;
import com.levelup.api.controller.v1.dto.response.member.MemberResponse;
import com.levelup.channel.domain.service.dto.ChannelMemberDto;
import com.levelup.channel.domain.service.ChannelMemberService;
import com.levelup.api.controller.v1.dto.response.channel.ChannelMemberResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "채널 멤버 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel/members")
public class ChannelMemberApiController {

    private final ChannelMemberService channelMemberService;

    @PostMapping({"", "/"})
    public ResponseEntity<ChannelMemberResponse> create(
            @RequestParam("channel") Long channelId,
            @RequestParam("member") Long memberId,
            @RequestBody CreateMemberRequest request)
    {
        ChannelMemberDto dto = channelMemberService.create(channelId, request.toDto(memberId));

        return ResponseEntity.ok().body(ChannelMemberResponse.from(dto));
    }


    @GetMapping({"", "/"})
    public ResponseEntity<Page<ChannelMemberResponse>> getChannelMembers(
            @RequestParam Long channelId,
            @RequestParam Boolean isWaitingMember,
            Pageable pageable)
    {
        Page<ChannelMemberResponse> responses
                = channelMemberService.getChannelMembers(channelId, isWaitingMember, pageable)
                .map(ChannelMemberResponse::from);

        return ResponseEntity.ok().body(responses);
    }


    @PatchMapping({"/{channelMemberId}", "/{channelMemberId}/"})
    public ResponseEntity<Void> approvalMember(@PathVariable Long channelMemberId) {
        channelMemberService.approvalMember(channelMemberId);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping({"/{channelMemberId}", "/{channelMemberId}/"})
    public ResponseEntity<Void> delete(
            @PathVariable Long channelMemberId,
            @RequestParam Long channelId)
    {
        channelMemberService.delete(channelMemberId, channelId);

        return ResponseEntity.ok().build();
    }
}
