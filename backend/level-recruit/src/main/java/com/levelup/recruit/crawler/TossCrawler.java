package com.levelup.recruit.crawler;

import com.levelup.recruit.crawler.scraper.TossScraper;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("TossCrawler")
public class TossCrawler implements Crawler {

    private final TossScraper tossScraper;

    @Override
    public Company getCompany() {
        return Company.TOSS;
    }

    @Override
    public List<Job> crawling() {
        return tossScraper.findJobs();
    }
}

