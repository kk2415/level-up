package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Job {

    protected Long id;
    protected String title;
    protected Company company;
    protected String url;
    protected OpenStatus openStatus;
    protected String noticeEndDate;

    public static Job of(String title, Company company, String url, OpenStatus openStatus, String noticeEndDate) {
        return new Job(null, title, company, url, openStatus, noticeEndDate);
    }

    public static Job from(JobEntity jobEntity) {
        return new Job(jobEntity.getId(), jobEntity.getTitle(), jobEntity.getCompany(), jobEntity.getUrl(), jobEntity.getOpenStatus(), jobEntity.getNoticeEndDate());
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, openStatus, noticeEndDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;

        return (company != null && company.equals(job.company)) && (url != null && url.equals(job.url));
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, url);
    }
}
