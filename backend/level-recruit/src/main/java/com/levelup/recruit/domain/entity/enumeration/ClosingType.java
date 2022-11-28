package com.levelup.recruit.domain.entity.enumeration;

/**
 * 채용 공고 마감일은 기간이 정해진 경우와 기간이 없는 상시 채용으로 구분된다.
 * */
public enum ClosingType {

    /*2022년 12월 27일 등 마감 기한이 있는 방식*/
    DEAD_LINE,

    /*상시 채용 등 마감 기한이 없는 방식*/
    INFINITE
    ;
}
