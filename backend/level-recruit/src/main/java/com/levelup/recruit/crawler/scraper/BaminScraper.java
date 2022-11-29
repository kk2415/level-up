package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.BaminJob;
import com.levelup.recruit.domain.domain.Job;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BaminScraper {

    private JsoupTemplate jsoupTemplate;

    public BaminScraper(@Qualifier("BaminConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        Elements jobList = jsoupTemplate.select("div.recruit-list > ul.recruit-type-list li");

        return jobList.stream().map(jobElement -> {
            String title = jsoupTemplate.selectSub(jobElement, "a.title > p.fr-view").text();
            String url = jsoupTemplate.selectSub(jobElement, "a.title").attr("href");
            String noticeEndDate = jsoupTemplate.selectSub(jobElement, "div.flag-type:nth-child(2)").text();

            return BaminJob.of(title, url, noticeEndDate);
        }).collect(Collectors.toUnmodifiableList());
    }
}
