package com.together.levelup.domain.comment;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ArticleIdentity {

    CHANNEL, POST, NOTICE, CHANNEL_NOTICE, QNA;

    @JsonCreator
    public static ArticleIdentity enumMatching(String enumName) {

        for (ArticleIdentity identity : ArticleIdentity.values()) {
            if (identity.name().equals(enumName)) {
                return identity;
            }
        }
        return POST;
    }

}
