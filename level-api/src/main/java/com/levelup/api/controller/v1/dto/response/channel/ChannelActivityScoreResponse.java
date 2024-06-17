package com.levelup.api.controller.v1.dto.response.channel;

import com.levelup.channel.domain.model.ChannelActivityScore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelActivityScoreResponse {

    private final Long channelId;
    private final Double mean;
    private final Double standardDeviation;
    private final Double aScore; //Activity Score

    public static ChannelActivityScoreResponse from(ChannelActivityScore channelActivityScore) {
        return new ChannelActivityScoreResponse(
                channelActivityScore.getChannelId(),
                channelActivityScore.getMean(),
                channelActivityScore.getStandardDeviation(),
                channelActivityScore.getaScore()
        );
    }
}
