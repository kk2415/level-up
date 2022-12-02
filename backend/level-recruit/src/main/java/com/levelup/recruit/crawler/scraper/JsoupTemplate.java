package com.levelup.recruit.crawler.scraper;

import com.levelup.common.exception.ErrorCode;
import com.levelup.recruit.crawler.connetion.JsoupConnectionMaker;
import com.levelup.recruit.exception.JsoupConnectionException;
import com.levelup.recruit.exception.JsoupHtmlParsingException;
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

    private Connection.Response requestHTML(Connection connection) {
        try {
            return connection.execute().bufferUp();
        } catch (IOException e) {
            throw new JsoupConnectionException(ErrorCode.JSOUP_FAIL_CONNECTING);
        }
    }

    private Document parseHTML(Connection.Response html) {
        try {
            return html.parse();
        } catch (IOException e) {
            throw new JsoupHtmlParsingException(ErrorCode.SECURITY_CODE_EXPIRED);
        }
    }
}
