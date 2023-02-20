package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

public class NaverJob extends Job {

    private NaverJob(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private NaverJob(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.NAVER,
                url,
                noticeEndDate);
    }

    public static NaverJob of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new NaverJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
