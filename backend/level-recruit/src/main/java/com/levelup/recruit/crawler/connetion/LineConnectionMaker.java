package com.levelup.recruit.crawler.connetion;

import com.levelup.recruit.domain.enumeration.Company;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component("LineConnectionMaker")
public class LineConnectionMaker implements JsoupConnectionMaker {

    @Override
    public Connection makeConnection() {
        String param = "?ca=Engineering&ci=Bundang,Seoul&co=East%20Asia&cp=LINE%20Plus,LINE%20Biz%20Plus,LINE%20Financial%20Plus,LINE%20Studio,LINE%20UP,LINE%20NEXT,LINE%20PLAY,LINE%20Investment%20Technologies%20&fi=Android,Client-side,iOS,Web%20Development,Server-side";

        return Jsoup.connect(Company.LINE.getUrl() + param);
    }
}
