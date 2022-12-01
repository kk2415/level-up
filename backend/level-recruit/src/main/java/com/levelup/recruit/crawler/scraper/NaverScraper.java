package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.LineJob;
import com.levelup.recruit.domain.domain.NaverJob;
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
        String param = "?subJobCdArr=1010001%2C1010002%2C1010003%2C1010004%2C1010005%2C1010006%2C1010007%2C1010008%2C1010020%2C1020001%2C1030001%2C1030002%2C1040001%2C1050001%2C1050002%2C1060001&sysCompanyCdArr=KR%2CNB%2CWM%2CSN%2CNL%2CWTKR%2CNFN%2CNI&empTypeCdArr=&entTypeCdArr=&workAreaCdArr=&sw=&subJobCdData=1010001&subJobCdData=1010002&subJobCdData=1010003&subJobCdData=1010004&subJobCdData=1010005&subJobCdData=1010006&subJobCdData=1010007&subJobCdData=1010008&subJobCdData=1010020&subJobCdData=1020001&subJobCdData=1030001&subJobCdData=1030002&subJobCdData=1040001&subJobCdData=1050001&subJobCdData=1050002&subJobCdData=1060001&sysCompanyCdData=KR&sysCompanyCdData=NB&sysCompanyCdData=WM&sysCompanyCdData=SN&sysCompanyCdData=NL&sysCompanyCdData=WTKR&sysCompanyCdData=NFN&sysCompanyCdData=NI";
        Elements jobList = jsoupTemplate.select(param, "ul.card_list > li");

        return jobList.stream().map(jobElement -> {
            String title = jsoupTemplate.selectSub(jobElement, "a.card_link > h4.card_title").text();

            String htmlOnClickAttrValue = jsoupTemplate.selectSub(jobElement, "a.card_link").attr("onclick");
            String jobNoticeKey = htmlOnClickAttrValue.substring(htmlOnClickAttrValue.indexOf("'") + 1, htmlOnClickAttrValue.lastIndexOf("'"));
            String url = "https://recruit.navercorp.com/rcrt/view.do?annoId=" + jobNoticeKey;

            String noticeEndDate = jsoupTemplate.selectSub(jobElement, "dl.card_info dt.blind:contains(모집 기간) + dd.info_text").text();

            return NaverJob.of(title, url, noticeEndDate);
        }).collect(Collectors.toUnmodifiableList());
    }
}
