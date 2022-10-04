package com.levelup.channel.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VoteType {

    ARTICLE, COMMENT;

    @JsonCreator
    public static VoteType enumMatching(String enumName) {
        for (VoteType voteType : VoteType.values()) {
            if (voteType.name().equals(enumName)) {
                return voteType;
            }
        }
        return ARTICLE;
    }

}
