package com.levelup.recruit.crawler.jsoupConnetion;

import com.levelup.recruit.domain.enumeration.Company;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Map.entry;

@Component("LineConnectionMaker")
public class LineConnectionMaker implements JsoupConnectionMaker {

    @Override
    public Connection makeConnection() {
        Map<String, String> header = Map.ofEntries(
                entry("authority", "careers.linecorp.com"),
                entry("path", "/ko/jobs?ca=Engineering&ci=Bundang,Seoul"),
                entry("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
                entry("accept-encoding", "zip, deflate, br"),
                entry("accept-language", "ko,en;q=0.9,ko-KR;q=0.8,en-US;q=0."),
                entry("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
        );

        return Jsoup.connect(Company.LINE.getUrl())
                .method(Connection.Method.GET)
                .headers(header)
                .data(Map.of("ca", "Engineering", "ci", "Bundang,Seoul"));
    }

    @Override
    public Connection makeConnection(String param) {
        System.out.println("url: " + Company.LINE.getUrl() + param);

        return Jsoup.connect(Company.LINE.getUrl() + param);
//                .data("ca", "Engineering", "ci=Bundang,Seoul");
    }
}
