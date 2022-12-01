package com.levelup.recruit.domain.repository;

import com.levelup.recruit.domain.domain.JobFilterCondition;
import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.OrderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobRepositoryCustom {

    Page<JobEntity> findByFilterCondition(JobFilterCondition filterCondition, OrderBy orderBy, Pageable pageable);
}
