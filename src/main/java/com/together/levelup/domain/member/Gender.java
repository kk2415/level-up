package com.together.levelup.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MAIL, FEMAIL;

    @JsonCreator
    public static Gender matchingEnum(String val){
        for(Gender gender : Gender.values()){
            if(gender.name().equals(val)){
                return gender;
            }
        }
        return Gender.MAIL;
    }

}
