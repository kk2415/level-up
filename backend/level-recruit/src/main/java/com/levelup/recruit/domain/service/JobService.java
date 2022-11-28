package com.levelup.recruit.domain.service;

import com.levelup.recruit.domain.repository.JobRepository;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.KakaoJob;
import com.levelup.recruit.domain.entity.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;

    public void saveIfAbsent(Company company, String title, String url, String rawClosingDate) {
        jobRepository.findByCompanyAndTitleOrCompanyAndUrl(company, title, url)
                .ifPresentOrElse(j -> {
                }, () -> {
                    KakaoJob kakaoJob = KakaoJob.of(title, url, rawClosingDate);

                    jobRepository.save(kakaoJob.toEntity());
                });
    }

    public void saveIfAbsent(List<Job> jobs) {
    }
}
