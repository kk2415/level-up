package com.levelup.recruit.crawler;

import com.levelup.recruit.crawler.scraper.BaminScraper;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.entity.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("BaminCrawler")
public class BaminCrawler implements Crawler {

    private final BaminScraper baminScraper;

    @Override
    public Company getCompany() {
        return Company.BAMIN;
    }

    @Override
    public List<Job> crawling() {
        return baminScraper.findJobs();
    }
}

