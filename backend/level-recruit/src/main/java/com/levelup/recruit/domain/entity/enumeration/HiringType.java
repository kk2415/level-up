package com.levelup.recruit.domain.entity.enumeration;

public enum HiringType {

    NONE("신입/경력"),
    NEW("신입"),
    EXPERIENCED("경력"),
    ;

    private String value;

    HiringType(String value) {
        this.value = value;
    }
}
