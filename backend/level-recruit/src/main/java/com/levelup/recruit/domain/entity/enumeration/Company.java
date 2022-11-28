package com.levelup.recruit.domain.entity.enumeration;

import lombok.Getter;

@Getter
public enum Company {

    NAVER("url"),
    KAKAO("https://careers.kakao.com/jobs"),
    LINE("url"),
    COPANG("url"),
    BAMIN("url"),
    ;

    /*채용 공고 사이트 주소*/
    private String url;

    Company(String url) {
        this.url = url;
    }
}
