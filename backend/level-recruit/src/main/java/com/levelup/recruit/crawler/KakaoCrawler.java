package com.levelup.recruit.crawler;

import com.levelup.recruit.crawler.scraper.KakaoScraper;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.entity.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("KakaoCrawler")
public class KakaoCrawler implements Crawler {

    private final KakaoScraper kakaoScraper;

    @Override
    public Company getCompany() {
        return Company.KAKAO;
    }

    @Override
    public List<Job> crawling() {
        return kakaoScraper.findJobs();
    }
}

