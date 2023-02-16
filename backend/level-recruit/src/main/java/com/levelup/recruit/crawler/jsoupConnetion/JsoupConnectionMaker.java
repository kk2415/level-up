package com.levelup.recruit.crawler.jsoupConnetion;

import org.jsoup.Connection;

public interface JsoupConnectionMaker {
    Connection makeConnection();
    Connection makeConnection(String param);
}
