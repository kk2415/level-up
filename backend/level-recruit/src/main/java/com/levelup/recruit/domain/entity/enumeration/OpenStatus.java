package com.levelup.recruit.domain.entity.enumeration;

/**
 * 채용 공고가 현재 모집 중인지 마감됐는지 표현
 * */
public enum OpenStatus {

    /*오늘 올라온 채용 공고*/
    TODAY,

    /*모집 중인 채용 공고*/
    OPEN,

    /*마감한 채용 공고*/
    CLOSING,
    ;
}
