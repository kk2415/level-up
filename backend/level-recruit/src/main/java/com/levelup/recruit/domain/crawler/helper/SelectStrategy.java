package com.levelup.recruit.domain.crawler.helper;

import org.jsoup.select.Elements;

@FunctionalInterface
public interface SelectStrategy {
    Elements get();
}
