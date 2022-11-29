package com.levelup.recruit.crawler;

import com.levelup.recruit.crawler.scraper.LineScraper;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.entity.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("LineCrawler")
public class LineCrawler implements Crawler {

    private final LineScraper lineScraper;

    @Override
    public Company getCompany() {
        return Company.LINE;
    }

    @Override
    public List<Job> crawling() {
        return lineScraper.findJobs();
    }
}

