package com.together.levelup.domain.post;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.together.levelup.domain.member.Gender;

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
