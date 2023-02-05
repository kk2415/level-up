package com.levelup.recruit.crawler;

import com.levelup.recruit.crawler.scraper.CoupangScraper;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("CoupangCrawler")
public class CoupangCrawler implements Crawler {

    private final CoupangScraper coupangScraper;

    @Override
    public Company getCompany() {
        return Company.COUPANG;
    }

    @Override
    public List<Job> crawling() {
        return coupangScraper.findJobs();
    }
}

