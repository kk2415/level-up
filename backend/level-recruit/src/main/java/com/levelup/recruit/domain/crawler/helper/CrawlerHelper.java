package com.levelup.recruit.domain.crawler.helper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public interface CrawlerHelper {

    Document getDocument(String rootUrl);

    Elements crawling(String selector, SelectStrategy strategy);
}
