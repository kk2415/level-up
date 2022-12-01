package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;

public class NaverJob extends Job {

    private NaverJob(
            String title,
            Company company,
            String url,
            OpenStatus openStatus,
            String noticeEndDate)
    {
        super(null, title, company, url, openStatus, noticeEndDate);
    }

    private NaverJob(
            String title,
            String url,
            String noticeEndDate)
    {
        super(null,
                title,
                Company.NAVER,
                url,
                OpenStatus.TODAY,
                noticeEndDate);
    }

    public static NaverJob of(
            String title,
            String url,
            String noticeEndDate)
    {
        return new NaverJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, openStatus, noticeEndDate);
    }
}
