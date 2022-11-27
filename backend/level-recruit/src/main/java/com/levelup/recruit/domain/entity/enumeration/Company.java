package com.levelup.recruit.domain.entity.enumeration;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum Company {

    NAVER("url"),
    KAKAO("https://careers.kakao.com/jobs"),
    LINE("url"),
    COPANG("url"),
    BAMIN("url"),
    ;

    private String page;

    Company(String page) {
        this.page = page;
    }
}
