package com.levelup.channel.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Stats {

    private final Double mean;
    private final Double standardDeviation;

    public static Stats from(Double mean, Double standardDeviation) {
        return new Stats(mean, standardDeviation);
    }
}
