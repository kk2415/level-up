package com.levelup.recruit.domain.service;

import com.levelup.recruit.domain.domain.JobFilterCondition;
import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.OrderBy;
import com.levelup.recruit.domain.repository.JobRepository;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;

    @Transactional
    public Job save(Job job) {
        JobEntity saveJob = jobRepository.save(job.toEntity());

        return Job.from(saveJob);
    }

    @Transactional
    public List<Job> saveIfAbsent(List<Job> jobs, Company company) {
        List<Job> findJobs = jobRepository.findByCompany(company).stream()
                .map(Job::from)
                .collect(Collectors.toUnmodifiableList());

        List<JobEntity> saveJobs = jobs.stream()
                .filter(job -> !findJobs.contains(job))
                .map(Job::toEntity)
                .collect(Collectors.toUnmodifiableList());

        jobRepository.saveAll(saveJobs);

        return saveJobs.stream()
                .map(Job::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<Job> filtering(JobFilterCondition filterCondition, OrderBy orderBy, Pageable pageable) {
        return jobRepository.findByFilterCondition(filterCondition, orderBy, pageable)
                .map(Job::from)
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<Job> getCreatedTodayByCompany(Company company, int page, int size) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        Pageable pageable = PageRequest.of(page, size);

        if (company == null) {
            return jobRepository.findByCreatedAt(startOfDay, endOfDay, pageable).stream()
                    .map(Job::from)
                    .collect(Collectors.toUnmodifiableList());
        }

        return jobRepository.findByCompanyAndCreatedAt(company, startOfDay, endOfDay, pageable).stream()
                .map(Job::from)
                .collect(Collectors.toUnmodifiableList());
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
    public void update(Long findJobId, Job updateJob) {
        JobEntity findJob = jobRepository.findById(findJobId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 채용 공고를 찾을 수 없습니다."));

        findJob.update(updateJob.getTitle(), updateJob.getUrl(), updateJob.getCompany(), updateJob.getNoticeEndDate());
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
