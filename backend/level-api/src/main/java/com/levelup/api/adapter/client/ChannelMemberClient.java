package com.levelup.api.adapter.client;

import com.levelup.api.controller.v1.dto.response.channel.ChannelMemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "level-channel", url = "http://localhost:8080/api/v1/channel-members")
public interface ChannelMemberClient {

    @PostMapping("")
    ChannelMemberResponse create(
            @RequestParam("channel") Long channelId,
            @RequestParam("member") Long memberId,
            @RequestParam("isManager") Boolean isManager,
            @RequestParam("isWaitingMember") Boolean isWaitingMember);
}
