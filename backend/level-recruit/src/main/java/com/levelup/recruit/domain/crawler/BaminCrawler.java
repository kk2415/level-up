package com.levelup.recruit.domain.crawler;

import com.levelup.recruit.domain.crawler.helper.KakaoCrawlerHelper;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.KakaoJob;
import com.levelup.recruit.domain.entity.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class BaminCrawler implements Crawler {

    private final KakaoCrawlerHelper crawlerHelper;
    private final String JOB_LIST_SELECTOR = "ul.list_jobs li";
    private final String TITLE_SELECTOR = "a.link_jobs > h4.tit_jobs";
    private final String URL_SELECTOR = "a.link_jobs";
    private final String CLOSING_DATE_SELECTOR = "dl.list_info > dt:contains(영입마감일) + dd";

    @Override
    public List<Job> crawling() {
        final String rootUrl = Company.BAMIN.getUrl();
        final Document doc = crawlerHelper.getDocument(rootUrl);

        return crawlingJobs(rootUrl, doc);
    }

    private List<Job> crawlingJobs(String rootUrl, Document doc) {


        return new ArrayList<>();
    }

    private String crawlingTitle(Element job) {
        return crawlerHelper.crawling(TITLE_SELECTOR, () -> job.select(TITLE_SELECTOR)).text();
    }

    private String crawlingURL(Element job) {
        return crawlerHelper.crawling(URL_SELECTOR, () -> job.select(URL_SELECTOR)).attr("href");
    }

    private String crawlingClosingDate(Element job) {
        return crawlerHelper.crawling(CLOSING_DATE_SELECTOR, () -> job.select(CLOSING_DATE_SELECTOR)).text();
    }
}
