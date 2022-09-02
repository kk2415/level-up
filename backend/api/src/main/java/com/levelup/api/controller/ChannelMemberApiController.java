package com.levelup.api.controller;

import com.levelup.api.service.ChannelMemberService;
import com.levelup.core.domain.member.Member;
import com.levelup.api.dto.channelMember.ChannelMemberResponse;
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


    /**
     * 생성
     * */
    @PostMapping("channel-members-for-test")
    public ResponseEntity<ChannelMemberResponse> createChannelMember(@RequestParam Long channelId,
                                                                     @RequestParam Long memberId) {
        ChannelMemberResponse response = channelMemberService.create(channelId, memberId, true);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("channel-members")
    public ResponseEntity<ChannelMemberResponse> createChannelMember(@RequestParam Long channelId,
                                              @AuthenticationPrincipal Member member) {
        ChannelMemberResponse response = channelMemberService.create(channelId, member.getId(), true);

        return ResponseEntity.ok().body(response);
    }


    /**
     * 조회
     * */
    @GetMapping("/channel-members")
    public ResponseEntity<Page<ChannelMemberResponse>> getChannelMembers(@RequestParam Long channelId,
                                                                         @RequestParam Boolean isWaitingMember,
                                                                         Pageable pageable) {
        log.error("=======start getChannelMembers========");
        Page<ChannelMemberResponse> responses = channelMemberService.getChannelMembers(channelId, isWaitingMember, pageable);

        return ResponseEntity.ok().body(responses);
    }


    /**
     * 수정
     * */
    @PatchMapping("/channel-members/{channelMemberId}")
    public ResponseEntity approvalMember(@PathVariable Long channelMemberId) {
        channelMemberService.approvalMember(channelMemberId);

        return ResponseEntity.ok().build();
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel-members/{channelMemberId}")
    public ResponseEntity deleteChannelMembers(@PathVariable Long channelMemberId,
                                                 @RequestParam Long channelId) {
        channelMemberService.delete(channelMemberId, channelId);

        return ResponseEntity.ok().build();
    }

}
