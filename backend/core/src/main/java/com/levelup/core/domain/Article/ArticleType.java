package com.levelup.core.domain.Article;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ArticleType {

    CHANNEL_POST, CHANNEL_NOTICE, NOTICE, QNA, POST, COMMENT;

    @JsonCreator
    public static ArticleType enumMatching(String enumName) {
        for (ArticleType identity : ArticleType.values()) {
            if (identity.name().equals(enumName)) {
                return identity;
            }
        }
        return CHANNEL_POST;
    }
}
