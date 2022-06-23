package com.levelup.core.domain.vote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.levelup.core.domain.Article.ArticleType;

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
