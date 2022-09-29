package com.levelup.api.controller.v1.channel;

import com.levelup.api.controller.v1.dto.request.channel.ChannelVoteRequest;
import com.levelup.api.controller.v1.dto.response.channel.ChannelVoteResponse;
import com.levelup.channel.domain.service.dto.ChannelVoteDto;
import com.levelup.channel.domain.service.vote.ChannelVoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "채널 추천 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel/votes")
public class ChannelVoteApiController {

    private final ChannelVoteService channelVoteServiceImpl;

    @PostMapping({"", "/"})
    public ResponseEntity<ChannelVoteResponse> create(@Valid @RequestBody ChannelVoteRequest request) {
        ChannelVoteDto dto = channelVoteServiceImpl.save(request.toDto());

        return ResponseEntity.ok().body(ChannelVoteResponse.from(dto));
    }
}
