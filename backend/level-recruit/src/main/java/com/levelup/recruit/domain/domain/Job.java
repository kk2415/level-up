package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.entity.enumeration.ClosingType;
import com.levelup.recruit.domain.entity.enumeration.Company;
import com.levelup.recruit.domain.entity.enumeration.JobStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Job {

    protected Long id;
    protected String title;
    protected Company company;
    protected String url;
    protected JobStatus jobStatus;
    protected ClosingType closingType;
    protected LocalDateTime openDate;
    protected LocalDateTime closingDate;

    public abstract JobEntity toEntity();
    public abstract ClosingType matchClosingType(String closingDate);
    public abstract LocalDateTime parseClosingDate(String closingDate);
}
