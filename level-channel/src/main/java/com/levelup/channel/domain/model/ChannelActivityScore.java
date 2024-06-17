package com.levelup.channel.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelActivityScore {

    private final Long channelId;
    private final Stats stats;
    private final Double aScore; //Activity Score

    public static ChannelActivityScore of(
            Long channelId,
            Stats stats,
            Double aScore
    ) {
        return new ChannelActivityScore(
                channelId,
                stats,
                aScore
        );
    }

    public Long getChannelId() {
        return channelId;
    }

    public Double getMean() {
        return stats.getMean();
    }

    public Double getStandardDeviation() {
        return stats.getStandardDeviation();
    }

    public Double getaScore() {
        return aScore;
    }
}
