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
        String param = "?ca=Engineering&ci=Bundang,Seoul&co=East%20Asia&cp=LINE%20Plus,LINE%20Biz%20Plus,LINE%20Financial%20Plus,LINE%20Studio,LINE%20UP,LINE%20NEXT,LINE%20PLAY,LINE%20Investment%20Technologies%20&fi=Android,Client-side,iOS,Web%20Development,Server-side";
        Elements jobList = jsoupTemplate.select(param, "ul.job_list > li");

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
