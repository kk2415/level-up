package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

public class TossJob extends Job {

    private TossJob(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private TossJob(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.TOSS,
                url,
                noticeEndDate);
    }

    public static TossJob of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new TossJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
