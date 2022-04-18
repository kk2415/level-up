package com.together.levelup.api;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.dto.ChannelResponse;
import com.together.levelup.dto.Result;
import com.together.levelup.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChannelApiController {

    private final ChannelService channelService;

    @GetMapping("/api/channels")
    public Result channels() {
        List<Channel> channels = channelService.findAll();

        List<ChannelResponse> responseList = channels.stream().map(c -> new ChannelResponse(
                                                    c.getName(), c.getLimitedMemberNumber(), c.getDateCreated(),
                                                    c.getManagerName(), c.getDescript(), c.getMemberCount()))
                                                .collect(Collectors.toList());

        return new Result(responseList, responseList.size());
    }

}
