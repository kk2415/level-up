package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;

public class LineJob extends Job {

    private LineJob(
            String title,
            Company company,
            String url,
            OpenStatus openStatus,
            String noticeEndDate)
    {
        super(null, title, company, url, openStatus, noticeEndDate);
    }

    private LineJob(
            String title,
            String url,
            String noticeEndDate)
    {
        super(null,
                title,
                Company.LINE,
                url,
                OpenStatus.TODAY,
                noticeEndDate);
    }

    public static LineJob of(
            String title,
            String url,
            String noticeEndDate)
    {
        return new LineJob(title, url, noticeEndDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, openStatus, noticeEndDate);
    }
}
