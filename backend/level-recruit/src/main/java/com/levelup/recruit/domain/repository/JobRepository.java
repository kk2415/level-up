package com.levelup.recruit.domain.repository;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.entity.enumeration.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query("select j from JobEntity j where (j.company = :company and j.title = :title) or (j.company = :company and j.url = :url)")
    Optional<JobEntity> findByCompanyAndTitleOrCompanyAndUrl(@Param("company") Company company, @Param("title") String title, @Param("url") String url);
}
