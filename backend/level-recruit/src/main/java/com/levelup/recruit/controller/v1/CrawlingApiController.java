package com.levelup.recruit.controller.v1;

import com.levelup.recruit.crawler.Crawler;
import com.levelup.recruit.domain.domain.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/jobs/crawling")
@RestController
public class CrawlingApiController {

    private Crawler kakaoCrawler;
    private Crawler baminCrawler;
    private Crawler LineCrawler;
    private Crawler naverCrawler;
    private Crawler tossCrawler;
    private Crawler coupangCrawler;

    @Autowired
    public CrawlingApiController(
            @Qualifier("KakaoCrawler") Crawler kakaoCrawler,
            @Qualifier("BaminCrawler") Crawler baminCrawler,
            @Qualifier("LineCrawler") Crawler lineCrawler,
            @Qualifier("NaverCrawler") Crawler naverCrawler,
            @Qualifier("TossCrawler") Crawler tossCrawler,
            @Qualifier("CoupangCrawler") Crawler coupangCrawler)
    {
        this.kakaoCrawler = kakaoCrawler;
        this.baminCrawler = baminCrawler;
        this.LineCrawler = lineCrawler;
        this.naverCrawler = naverCrawler;
        this.tossCrawler = tossCrawler;
        this.coupangCrawler = coupangCrawler;
    }

    @PostMapping("/kakao")
    public ResponseEntity<Void> crawlingKakao() {
        List<Job> crawling = kakaoCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/bamin")
    public ResponseEntity<Void> crawlingBamin() {
        List<Job> crawling = baminCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/line")
    public ResponseEntity<Void> crawlingLine() {
        List<Job> crawling = LineCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/naver")
    public ResponseEntity<Void> crawlingNaver() {
        List<Job> crawling = naverCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/toss")
    public ResponseEntity<Void> crawlingToss() {
        List<Job> crawling = tossCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/coupang")
    public ResponseEntity<Void> crawlingCoupang() {
        List<Job> crawling = coupangCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }
}
