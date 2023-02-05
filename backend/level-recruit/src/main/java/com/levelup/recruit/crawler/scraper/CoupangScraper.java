package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.CoupangJob;
import com.levelup.recruit.domain.domain.Job;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoupangScraper {

    private final JsoupTemplate jsoupTemplate;

    public CoupangScraper(@Qualifier("CoupangConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        int page = 1;
        int lastPage = 10;
        String param;

        List<Job> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            param = "?page=" + page + "&search=Software+Engineer&location=Seoul%2c+South+Korea&pagesize=20#results";
            Elements jobList = jsoupTemplate.select(param, "div.job-listing > div.card-job");

            List<CoupangJob> scrapedJobs = jobList.stream().map(job -> {
                final String title = jsoupTemplate.selectSub(job, "div.card-body > h2.card-title > a.stretched-link").text();
                final String url = "https://www.coupang.jobs" + jsoupTemplate.selectSub(job, "div.card-body > h2.card-title > a.stretched-link").attr("href");
                final String noticeEndDate = "채용 마감시";

                return CoupangJob.of(title, url, noticeEndDate);
            }).collect(Collectors.toUnmodifiableList());

            jobs.addAll(scrapedJobs);
        }

        return jobs;
    }
}
