package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;

public class TossJob extends Job {

    private TossJob(
            String title,
            Company company,
            String url,
            OpenStatus openStatus,
            String noticeEndDate)
    {
        super(null, title, company, url, openStatus, noticeEndDate);
    }

    private TossJob(
            String title,
            String url,
            String noticeEndDate)
    {
        super(null,
                title,
                Company.TOSS,
                url,
                OpenStatus.TODAY,
                noticeEndDate);
    }

    public static TossJob of(
            String title,
            String url,
            String noticeEndDate)
    {
        return new TossJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, openStatus, noticeEndDate);
    }
}
