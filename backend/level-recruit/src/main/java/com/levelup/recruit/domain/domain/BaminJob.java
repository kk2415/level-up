package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

public class BaminJob extends Job {

    private BaminJob(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private BaminJob(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.BAMIN,
                url,
                noticeEndDate);
    }

    public static BaminJob of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new BaminJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
