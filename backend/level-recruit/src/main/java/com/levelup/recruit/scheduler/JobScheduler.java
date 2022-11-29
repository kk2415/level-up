package com.levelup.recruit.scheduler;

import com.levelup.recruit.crawler.Crawler;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JobScheduler {

    private final List<Crawler> crawlers;
    private final JobService jobService;

    @Scheduled(cron = "30 * * * * * ")
    public void crawlingJobList() {
        crawlers.forEach(crawler -> {
            List<Job> jobs = crawler.crawling();

            jobService.saveIfAbsent(jobs, crawler.getCompany());

            List<Job> deleteJobs = jobService.getNotMatched(jobs, crawler.getCompany());
            jobService.deleteAll(deleteJobs);
        });
    }
}
