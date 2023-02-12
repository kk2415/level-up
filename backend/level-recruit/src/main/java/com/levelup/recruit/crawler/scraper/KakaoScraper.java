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
public class KakaoScraper {

    private final JsoupTemplate jsoupTemplate;

    public KakaoScraper(@Qualifier("KakaoConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        int page = 1;
        int lastPage = 5;
        String param;

        List<Job> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            param = "?skilset=Android,iOS,Windows,Web_front,DB,Cloud,Server,Hadoop_eco_system,Algorithm_Ranking,System" +
                    "&company=ALL" +
                    "&page=" + page;
            Elements jobList = jsoupTemplate.select(param, "ul.list_jobs li");

            List<KakaoJob> scrapedJobs = jobList.stream().map(job -> {
                final String title = jsoupTemplate.selectSub(job, "a.link_jobs > h4.tit_jobs").text();
                final String url = Company.KAKAO.getUrl() + jsoupTemplate.selectSub(job, "a.link_jobs").attr("href");
                final String noticeEndDate = jsoupTemplate.selectSub(job, "dl.list_info > dt:contains(영입마감일) + dd").text();

                return KakaoJob.of(title, url, noticeEndDate);
            }).collect(Collectors.toUnmodifiableList());

            jobs.addAll(scrapedJobs);
        }

        return jobs;
    }
}
