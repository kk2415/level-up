package com.levelup.recruit.config;

import com.levelup.recruit.crawler.Crawler;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 스프링 컨테이너 초기화 직후 실행됨
 * 필요에 따라 ContextStartedEvent 대신 상황에 맞게 다른 이벤트를 넣어줄 수도 있다
 * */
@Slf4j
@RequiredArgsConstructor
@Component
public class SpringApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final List<Crawler> crawlers;
    private final JobService jobService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Starting ContextRefreshedEvent of SpringApplicationListener");

        crawlers.forEach(crawler -> {
            List<Job> jobs = crawler.crawling();

            jobService.saveIfAbsent(jobs, crawler.getCompany());

            List<Job> deleteJobs = jobService.getNotMatched(jobs, crawler.getCompany());
            jobService.deleteAll(deleteJobs);
        });
    }
}
