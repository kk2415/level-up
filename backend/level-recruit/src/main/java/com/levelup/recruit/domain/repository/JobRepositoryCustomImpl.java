package com.levelup.recruit.domain.repository;

import com.levelup.recruit.domain.domain.JobFilterCondition;
import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.entity.QJobEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import com.levelup.recruit.domain.enumeration.OrderBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class JobRepositoryCustomImpl implements JobRepositoryCustom {

    private final EntityManager em;

    @Override
    public Page<JobEntity> findByFilterCondition(JobFilterCondition filterCondition, OrderBy orderBy, Pageable pageable) {
        QJobEntity job = QJobEntity.jobEntity;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<JobEntity> jobs = queryFactory
                .select(job)
                .from(job)
                .where(filterCompany(filterCondition.getCompany()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(job.createdAt.desc())
                .fetch();

        return new PageImpl<>(jobs, pageable, jobs.size());
    }

    private BooleanExpression filterCompany(Company company) {
        return company == null ? null : QJobEntity.jobEntity.company.eq(company);
    }
}
