package com.levelup.recruit.domain.repository;

import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.JobFilterCondition;
import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import com.levelup.recruit.domain.enumeration.OrderBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("dev")
@DisplayName("채용 공고 DB CRUD 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class JobRepositoryTest {

    private final JobRepository jobRepository;

    public JobRepositoryTest(@Autowired JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @BeforeEach
    public void before() {
        jobRepository.deleteAll();
    }

    @Test
    public void given_jobs_when_filterKaKaoCompany_then_returnOnlyKakaoJobs() {
        //given
        List<JobEntity> saveJobs = List.of(JobEntity.of("백엔드 채용 공고", Company.KAKAO, "url", "마감까지"),
                JobEntity.of("프론트엔드 채용 공고", Company.KAKAO, "url", "마감까지"),
                JobEntity.of("데브옵스 채용 공고", Company.KAKAO, "url", "마감까지"),
                JobEntity.of("머신러닝 채용 공고", Company.BAMIN, "url", "마감까지"),
                JobEntity.of("안드로이드 채용 공고", Company.COUPANG, "url", "마감까지"));
        jobRepository.saveAll(saveJobs);

        JobFilterCondition filterCondition = JobFilterCondition.of(Company.KAKAO, null);

        //when
        List<Job> findJobs = jobRepository.findByFilterCondition(filterCondition, OrderBy.CREATED_AT, PageRequest.of(0, 10))
                .map(Job::from).stream().collect(Collectors.toUnmodifiableList());

        //then
        assertThat(findJobs.size()).isEqualTo(3);
        for (Job findJob : findJobs) {
            assertThat(findJob.getCompany()).isEqualTo(Company.KAKAO);
        }
    }

    @Test
    public void given_jobs_when_filterNothing_then_returnAll() {
        //given
        List<JobEntity> saveJobs = List.of(JobEntity.of("백엔드 채용 공고", Company.KAKAO, "url", "마감까지"),
                JobEntity.of("프론트엔드 채용 공고", Company.KAKAO, "url", "마감까지"),
                JobEntity.of("데브옵스 채용 공고", Company.KAKAO, "url", "마감까지"),
                JobEntity.of("머신러닝 채용 공고", Company.BAMIN, "url", "마감까지"),
                JobEntity.of("안드로이드 채용 공고", Company.COUPANG, "url", "마감까지"));
        jobRepository.saveAll(saveJobs);

        JobFilterCondition filterCondition = JobFilterCondition.of(null, null);

        //when
        List<Job> findJobs = jobRepository.findByFilterCondition(filterCondition, OrderBy.CREATED_AT, PageRequest.of(0, 10))
                .map(Job::from).stream().collect(Collectors.toUnmodifiableList());

        //then
        assertThat(findJobs.size()).isEqualTo(5);
    }
}
