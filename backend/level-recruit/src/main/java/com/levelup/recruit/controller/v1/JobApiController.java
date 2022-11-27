package com.levelup.recruit.controller.v1;

import com.levelup.recruit.domain.entity.enumeration.ClosingType;
import com.levelup.recruit.domain.entity.enumeration.Company;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class JobApiController {

    @GetMapping("/api/v1/jobs")
    public void test() {

    }

    @PostMapping("")
    public ResponseEntity<Void> get() {
        try {
            final String homePage = Company.KAKAO.getPage();
            Document doc = Jsoup.connect("https://careers.kakao.com/jobs?page=1&employeeType=&keyword=&company=ALL").execute().bufferUp().parse();

            Elements jobs = doc.select("ul.list_jobs li");
            for (Element job : jobs) {
                String title = job.select("a.link_jobs > h4.tit_jobs").text();
                String url = homePage + job.select("a.link_jobs").attr("href");
                String closingDate = job.select("dl.list_info > dt:contains(영입마감일) + dd").text();
                ClosingType closingType = getClosingType(closingDate);

                if (ClosingType.DEAD_LINE.equals(closingType)) {
                    parse(closingDate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

    private ClosingType getClosingType(String closingDate) {
        if (closingDate.equals("영입종료시")) {
            return ClosingType.INFINITE;
        }
        return ClosingType.DEAD_LINE;
    }

    private void parse(String closingDate) {
        //ex) 2022년 12월 24일 까지
        String newClosing = closingDate.substring(0, closingDate.length() - 3);
        LocalDate parse = LocalDate.parse(newClosing, DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        System.out.println(parse);

        System.out.println(closingDate);
        List<String> date = Arrays.stream(closingDate.split(" "))
                .limit(3)
                .map(s -> s.substring(0, s.length() - 1))
                .collect(Collectors.toList());

        System.out.println(date);
    }
}
