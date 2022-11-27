package com.levelup.api.controller.v1.test;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
public class JsoupController {

    @GetMapping("/test")
    public void test(@RequestParam String url) throws IOException {

        // 인프런 페이지
        final String inflearnUrl = "https://www.inflearn.com/courses/it-programming";

        try {
            Connection conn = Jsoup.connect(inflearnUrl);
            Document document = conn.get();
            Elements imageUrlElements = document.getElementsByClass("swiper-lazy");

            for (Element element : imageUrlElements) {
                System.out.println(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
