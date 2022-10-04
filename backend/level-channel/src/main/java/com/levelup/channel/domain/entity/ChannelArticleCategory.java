package com.levelup.channel.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ChannelArticleCategory {
    INFO, LIFE, TIP, TALK, SELF_IMPROVEMENT, INTRODUCE;

    @JsonCreator
    public static ChannelArticleCategory matchingEnum(String val){
        for(ChannelArticleCategory category : ChannelArticleCategory.values()){
            if(category.name().equals(val)){
                return category;
            }
        }
        return ChannelArticleCategory.TALK;
    }
}
