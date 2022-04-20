package com.together.levelup.api;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.dto.ChannelResponse;
import com.together.levelup.dto.PostRequest;
import com.together.levelup.dto.PostResponse;
import com.together.levelup.dto.Result;
import com.together.levelup.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelApiController {

    private final ChannelService channelService;

    @GetMapping("/channels")
    public Result channels() {
        List<Channel> channels = channelService.findAll();

        List<ChannelResponse> responseList = channels.stream().map(c -> new ChannelResponse(
                                                    c.getName(), c.getLimitedMemberNumber(), c.getDateCreated(),
                                                    c.getManagerName(), c.getDescript(), c.getMemberCount()))
                                                .collect(Collectors.toList());

        return new Result(responseList, responseList.size());
    }

    @PostMapping("/channel")
    public PostResponse create(@RequestBody @Validated PostRequest postRequest) {
        Long channelId = channelService.create(postRequest.getMemberEmail(), postRequest.getName(),
                postRequest.getLimitedMemberNumber(), postRequest.getDescription());
        return new PostResponse(postRequest.getName(), postRequest.getLimitedMemberNumber(), postRequest.getManagerName(), postRequest.getDescription(), 0L);
    }

}
