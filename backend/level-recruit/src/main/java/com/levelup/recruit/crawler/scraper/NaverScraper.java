package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.LineJob;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NaverScraper {

    private JsoupTemplate jsoupTemplate;

    public NaverScraper(@Qualifier("NaverConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<Job> findJobs() {
        Elements jobList = jsoupTemplate.select("ul.card_list > li");

        return jobList.stream().map(jobElement -> {
            String title = jsoupTemplate.selectSub(jobElement, "a.card_link > h4.card_title").text();

            String htmlOnClickAttrValue = jsoupTemplate.selectSub(jobElement, "a.card_link").attr("onclick");
            String jobNoticeKey = htmlOnClickAttrValue.substring(htmlOnClickAttrValue.indexOf("'") + 1, htmlOnClickAttrValue.lastIndexOf("'"));
            String url = "https://recruit.navercorp.com/rcrt/view.do?annoId=" + jobNoticeKey;

            String noticeEndDate = jsoupTemplate.selectSub(jobElement, "dl.card_info dt.blind:contains(모집 기간) + dd.info_text").text();

            return LineJob.of(title, url, noticeEndDate);
        }).collect(Collectors.toUnmodifiableList());
    }
}
