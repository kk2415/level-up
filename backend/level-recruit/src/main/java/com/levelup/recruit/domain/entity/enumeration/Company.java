package com.levelup.recruit.domain.entity.enumeration;

import lombok.Getter;

@Getter
public enum Company {

    NAVER("url"),
    KAKAO("https://careers.kakao.com/jobs"),
    LINE("https://careers.linecorp.com/ko/jobs"),
    COPANG("url"),
    BAMIN("https://career.woowahan.com/?keyword=&category=jobGroupCodes%3ABA005001#recruit-list"),
    ;

    /*채용 공고 사이트 주소*/
    private String url;

    Company(String url) {
        this.url = url;
    }
}
