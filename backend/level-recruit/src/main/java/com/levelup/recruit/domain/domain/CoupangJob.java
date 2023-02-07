package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;

public class CoupangJob extends Job {

    private CoupangJob(
            String title,
            Company company,
            String url,
            OpenStatus openStatus,
            String noticeEndDate)
    {
        super(null, title, company, url, openStatus, noticeEndDate);
    }

    private CoupangJob(
            String title,
            String url,
            String noticeEndDate)
    {
        super(null,
                title,
                Company.COUPANG,
                url,
                OpenStatus.TODAY,
                noticeEndDate);
    }

    public static CoupangJob of(
            String title,
            String url,
            String noticeEndDate)
    {
        return new CoupangJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, openStatus, noticeEndDate);
    }
}
