package com.levelup.recruit.domain.entity;

import com.levelup.recruit.domain.entity.base.BaseTimeEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "job")
@Entity
public class JobEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "job_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OpenStatus openStatus;

    private String noticeEndDate;

    public static JobEntity of(
            String title,
            Company company,
            String url,
            OpenStatus openStatus,
            String noticeEndDate)
    {
        return new JobEntity(null, title, url, company, openStatus, noticeEndDate);
    }

    public void update(String title, String url, Company company, OpenStatus openStatus, String noticeEndDate) {
        this.title = title;
        this.url = url;
        this.company = company;
        this.openStatus = openStatus;
        this.noticeEndDate = noticeEndDate;
    }
}