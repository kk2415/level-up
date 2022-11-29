package com.levelup.recruit.crawler.connetion;

import com.levelup.recruit.domain.entity.enumeration.Company;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component("KakaoConnectionMaker")
public class KakaoConnectionMaker implements JsoupConnectionMaker {

    @Override
    public Connection makeConnection() {
        String param = "?company=ALL";

        return Jsoup.connect(Company.KAKAO.getUrl() + param);
    }
}
