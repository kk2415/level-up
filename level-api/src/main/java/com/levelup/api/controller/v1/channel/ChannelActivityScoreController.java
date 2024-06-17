package com.levelup.api.controller.v1.channel;

import com.levelup.api.controller.v1.dto.response.channel.ChannelActivityScoreResponse;
import com.levelup.channel.domain.model.ChannelActivityScore;
import com.levelup.channel.domain.service.ChannelActivityScoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "채널 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel/activity-scores")
@RestController
public class ChannelActivityScoreController {

    private final ChannelActivityScoreService channelActivityScore;

    @GetMapping("/top10")
    public ResponseEntity<List<ChannelActivityScoreResponse>> top10() {
        List<ChannelActivityScore> weeklyActivityScoresTop10 = channelActivityScore.findWeeklyActivityScoresTop10();

        return ResponseEntity.ok().body(weeklyActivityScoresTop10.stream()
                .map(ChannelActivityScoreResponse::from)
                .collect(Collectors.toList())
        );
    }

    @GetMapping
    public ResponseEntity<ChannelActivityScoreResponse> get(@RequestParam Long channelId) {
        ChannelActivityScore weeklyActivityScore = channelActivityScore.findWeeklyActivityScore(channelId);

        return ResponseEntity.ok().body(ChannelActivityScoreResponse.from(weeklyActivityScore));
    }
}
