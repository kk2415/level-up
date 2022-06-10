package com.levelup.core.domain.Article;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ArticleCategory {

    CHANNEL_POST, CHANNEL_NOTICE, NOTICE, QNA, COMMENT, CHANNEL, POST;

    @JsonCreator
    public static ArticleCategory enumMatching(String enumName) {

        for (ArticleCategory identity : ArticleCategory.values()) {
            if (identity.name().equals(enumName)) {
                return identity;
            }
        }
        return POST;
    }

}
