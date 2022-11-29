package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.entity.enumeration.Company;
import com.levelup.recruit.domain.entity.enumeration.OpenStatus;

public class BaminJob extends Job {

    private BaminJob(
            String title,
            Company company,
            String url,
            OpenStatus openStatus,
            String noticeEndDate)
    {
        super(null, title, company, url, openStatus, noticeEndDate);
    }

    private BaminJob(
            String title,
            String url,
            String noticeEndDate)
    {
        super(null,
                title,
                Company.BAMIN,
                url,
                OpenStatus.TODAY,
                noticeEndDate);
    }

    public static BaminJob of(
            String title,
            String url,
            String noticeEndDate)
    {
        return new BaminJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, openStatus, noticeEndDate);
    }
}
