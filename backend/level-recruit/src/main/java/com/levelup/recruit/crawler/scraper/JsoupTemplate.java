package com.levelup.recruit.crawler.scraper;

import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTemplate {

    private final JsoupConnectionMaker connectionMaker;

    private JsoupTemplate(JsoupConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public static JsoupTemplate from(JsoupConnectionMaker connectionMaker) {
        return new JsoupTemplate(connectionMaker);
    }

    public Elements select(String param, String selector) {
        Connection connection = connectionMaker.makeConnection(param);

        Connection.Response html = requestHTML(connection);
        Document doc = parseHTML(html);

        return doc.select(selector);
    }

    public Elements select(String selector) {
        Connection connection = connectionMaker.makeConnection();

        Connection.Response html = requestHTML(connection);
        Document doc = parseHTML(html);

        return doc.select(selector);
    }

    public Elements selectSub(Element element, String selector) {
        return element.select(selector);
    }

    private Connection.Response requestHTML(Connection connect) {
        try {
            return connect.execute().bufferUp();
        } catch (IOException e) {
            throw new IllegalStateException("HTML을 불러 올 수 없습니다.");
        }
    }

    private Document parseHTML(Connection.Response html) {
        try {
            return html.parse();
        } catch (IOException e) {
            throw new IllegalStateException("HTTML 파싱 예외 발생");
        }
    }
}
