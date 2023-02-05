package com.levelup.recruit.domain.enumeration;

import lombok.Getter;

@Getter
public enum Company {

    NAVER("https://recruit.navercorp.com/rcrt/list.do"),
    KAKAO("https://careers.kakao.com/jobs"),
    LINE("https://careers.linecorp.com/ko/jobs"),
    COUPANG("https://www.coupang.jobs/kr/jobs"),
    BAMIN("https://career.woowahan.com/?keyword=&category=jobGroupCodes%3ABA005001#recruit-list"),

    TOSS("https://toss.im/career/jobs"),
    BUCKET_PLACE("https://www.bucketplace.com/careers/"),
    CARROT_MARKET("https://team.daangn.com/jobs/"),
    NC("https://careers.ncsoft.com/apply/list"),

    OTHER("url"),
    ;

    /*채용 공고 사이트 주소*/
    private String url;

    Company(String url) {
        this.url = url;
    }
}
