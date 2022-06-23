package com.levelup.core.domain.Article;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PostCategory {
    INFO, LIFE, TIP, TALK, SELF_IMPROVEMENT, INTRODUCE;

    @JsonCreator
    public static PostCategory matchingEnum(String val){
        for(PostCategory category : PostCategory.values()){
            if(category.name().equals(val)){
                return category;
            }
        }
        return PostCategory.TALK;
    }
}
