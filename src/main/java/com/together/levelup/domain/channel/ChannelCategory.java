package com.together.levelup.domain.channel;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ChannelCategory {

    STUDY, PROJECT;

    @JsonCreator
    public static ChannelCategory matchingNum(String category) {
        for (ChannelCategory channelCategory : ChannelCategory.values()) {
            if (channelCategory.name().equals(category)) {
                return channelCategory;
            }
        }
        return STUDY;
    }

}
