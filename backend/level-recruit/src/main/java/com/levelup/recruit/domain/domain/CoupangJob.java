package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

public class CoupangJob extends Job {

    private CoupangJob(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private CoupangJob(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.COUPANG,
                url,
                noticeEndDate);
    }

    public static CoupangJob of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new CoupangJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
