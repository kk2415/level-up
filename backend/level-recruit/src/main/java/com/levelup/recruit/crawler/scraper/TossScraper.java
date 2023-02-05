package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.KakaoJob;
import com.levelup.recruit.domain.enumeration.Company;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TossScraper {

    private final JsoupTemplate jsoupTemplate;

    public TossScraper(@Qualifier("TossConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        int page = 1;
        int lastPage = 3;
        String param;

        param = "?isNewCareer=true&category=engineering-product";
        String text = jsoupTemplate.select(param, "button.text-button typography-t5 text-button--type-underline css-oe6f23 eco6lsy2")
                .text();
        System.out.println(text);

        Elements jobList = jsoupTemplate.select(param, "div.css-64lvsl > a.css-g65o95");
        System.out.println(jobList.size());

        List<Job> jobs = new ArrayList<>();
//        for (; page <= lastPage; ++page) {
//            param = "?isNewCareer=true&category=engineering-product";
//            Elements jobList = jsoupTemplate.select(param, "div.css-64lvsl > a.css-g65o95");
//            System.out.println(jobList.size());
//
//            List<KakaoJob> scrapedJobs = jobList.stream().map(job -> {
//                final String title = jsoupTemplate.selectSub(job, "div.css-1xr69i7 > span.css-16tmfdr").text();
//                System.out.println(title);
//
//                final String url = Company.KAKAO.getUrl() + jsoupTemplate.selectSub(job, "a.link_jobs").attr("href");
//                final String noticeEndDate = jsoupTemplate.selectSub(job, "dl.list_info > dt:contains(영입마감일) + dd").text();
//
//                return KakaoJob.of(title, url, noticeEndDate);
//            }).collect(Collectors.toUnmodifiableList());
//
//            jobs.addAll(scrapedJobs);
//        }

        return jobs;
    }
}
