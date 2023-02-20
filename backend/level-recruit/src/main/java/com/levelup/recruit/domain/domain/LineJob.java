package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

public class LineJob extends Job {

    private LineJob(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private LineJob(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.LINE,
                url,
                noticeEndDate);
    }

    public static LineJob of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new LineJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
