package com.levelup.channel.domain.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ChannelSort {

    MEMBER_COUNT("memberCount"),
    ID("id");

    private String sort;

    ChannelSort(String sort) {
        this.sort = sort;
    }

    @JsonCreator
    public static ChannelSort enumMatching(String enumName) {
        for (ChannelSort sort : ChannelSort.values()) {
            if (sort.name().equals(enumName)) {
                return sort;
            }
        }
        return ID;
    }
}
