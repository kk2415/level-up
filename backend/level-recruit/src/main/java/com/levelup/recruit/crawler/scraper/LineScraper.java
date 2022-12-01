package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.LineJob;
import com.levelup.recruit.domain.enumeration.Company;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LineScraper {

    private JsoupTemplate jsoupTemplate;

    public LineScraper(@Qualifier("LineConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        Elements jobList = jsoupTemplate.select("ul.job_list > li");

        return jobList.stream().map(jobElement -> {
            String title = jsoupTemplate.selectSub(jobElement, "a h3.title").text();

            String jobNoticeUri = jsoupTemplate.selectSub(jobElement, "a").attr("href");
            String jobNoticePath = jobNoticeUri.substring(jobNoticeUri.lastIndexOf("/"));
            String url = Company.LINE.getUrl() + jobNoticePath;

            String noticeEndDate = jsoupTemplate.selectSub(jobElement, "a span.date").text();

            return LineJob.of(title, url, noticeEndDate);
        }).collect(Collectors.toUnmodifiableList());
    }
}
