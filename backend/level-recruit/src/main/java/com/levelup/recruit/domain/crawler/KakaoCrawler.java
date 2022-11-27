package com.levelup.recruit.domain.crawler;

import com.levelup.recruit.domain.entity.enumeration.ClosingType;
import com.levelup.recruit.domain.entity.enumeration.Company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KakaoCrawler implements Crawler {

    @Override
    public void crawling() {
        try {
            final String page = Company.KAKAO.getPage();

            Document doc = Jsoup.connect(Company.KAKAO.getPage()).execute().bufferUp().parse();

            Elements jobs = doc.select("ul.list_jobs li");
            for (Element job : jobs) {
                String title = job.select("a.link_jobs > h4.tit_jobs").text();
                String url = page + job.select("a.link_jobs").attr("href");
                String closingDate = job.select("dl.list_info > dt:contains(영입마감일) + dd").text();
                ClosingType closingType = getClosingType(closingDate);



            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ClosingType getClosingType(String closingDate) {
        if (closingDate.equals("영입종료시")) {
            return ClosingType.INFINITE;
        }
        return ClosingType.DEAD_LINE;
    }
}
