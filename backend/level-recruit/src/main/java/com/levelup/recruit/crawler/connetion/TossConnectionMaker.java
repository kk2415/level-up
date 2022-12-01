package com.levelup.recruit.crawler.connetion;

import com.levelup.recruit.domain.enumeration.Company;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component("TossConnectionMaker")
public class TossConnectionMaker implements JsoupConnectionMaker {

    @Override
    public Connection makeConnection() {
        return Jsoup.connect(Company.NAVER.getUrl());
    }

    @Override
    public Connection makeConnection(String param) {
        return Jsoup.connect(Company.NAVER.getUrl() + param);
    }
}
