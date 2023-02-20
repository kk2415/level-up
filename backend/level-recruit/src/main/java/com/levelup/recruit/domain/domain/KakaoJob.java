package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.ClosingType;
import com.levelup.recruit.domain.enumeration.Company;

public class KakaoJob extends Job {

    /*카카오 공고는 상시 채용일 경우 마김 일자가 "영입종료시" 으로 되어있다.*/
    private static String INFINITE_CLOSING_TYPE = "영입종료시";

    private KakaoJob(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private KakaoJob(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.KAKAO,
                url,
                noticeEndDate);
    }

    public static KakaoJob of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new KakaoJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }

    /**
     * 채용 공고 마감 방식이 상시 채용인지 마감 기한이 있는지 체크
     * */
    public ClosingType matchClosingType(String closingDate) {
        if (closingDate.equals(INFINITE_CLOSING_TYPE)) {
            return ClosingType.INFINITE;
        }
        return ClosingType.DEAD_LINE;
    }
}
