package com.levelup.recruit.domain.service;

import com.levelup.recruit.domain.domain.KakaoJob;
import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.entity.enumeration.OpenStatus;
import com.levelup.recruit.domain.repository.JobRepository;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.entity.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;

    @Transactional
    public void saveIfAbsent(List<Job> jobs, Company company) {
        List<Job> findJobs = jobRepository.findByCompany(company).stream()
                .map(Job::from)
                .collect(Collectors.toUnmodifiableList());

        List<JobEntity> saveJobs = jobs.stream()
                .filter(job -> !findJobs.contains(job))
                .map(Job::toEntity)
                .collect(Collectors.toUnmodifiableList());

        jobRepository.saveAll(saveJobs);
    }

    @Transactional(readOnly = true)
    public List<Job> getNotMatched(List<Job> jobs, Company company) {
        List<Job> findJobs = jobRepository.findByCompany(company).stream()
                .map(Job::from)
                .collect(Collectors.toUnmodifiableList());

        return findJobs.stream()
                .filter(findJob -> !jobs.contains(findJob))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void deleteAll(List<Job> jobs) {
        jobRepository.deleteAll(
                jobs.stream()
                .map(Job::toEntity)
                .collect(Collectors.toUnmodifiableList())
        );
    }
}
