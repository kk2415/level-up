package com.levelup.recruit.domain.crawler.helper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class KakaoCrawlerHelper implements CrawlerHelper {

    @Override
    public Document getDocument(String rootUrl) {
        Connection connect = makeConnection(rootUrl);
        Connection.Response html = requestHTML(connect);

        return parseHTML(html);
    }

    private Connection makeConnection(String rootUrl) {
        return Jsoup.connect(rootUrl);
    }

    private Connection.Response requestHTML(Connection connect) {
        try {
            return connect.execute().bufferUp();
        } catch (IOException e) {
            throw new IllegalStateException("");
        }
    }

    private Document parseHTML(Connection.Response html) {
        try {
            return html.parse();
        } catch (IOException e) {
            throw new IllegalStateException("");
        }
    }

    @Override
    public Elements crawling(String selector, SelectStrategy strategy) {
        Elements result = new Elements();

        try {
            result = strategy.get();
        } catch (Exception e) {
            log.error("[카카오 공고 예외] - {}", selector);
        }

        return result;
    }
}
