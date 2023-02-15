package com.levelup.recruit.controller.v1;

import com.levelup.recruit.crawler.Crawler;
import com.levelup.recruit.domain.domain.Job;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "채용 사이트 크롤링 API")
@RequestMapping("/api/v1/jobs/crawling")
@RestController
public class CrawlingApiController {

    private Crawler kakaoCrawler;
    private Crawler LineCrawler;
    private Crawler naverCrawler;
    private Crawler coupangCrawler;
    private Crawler tossCrawler;

    @Autowired
    public CrawlingApiController(
            @Qualifier("KakaoCrawler") Crawler kakaoCrawler,
            @Qualifier("LineCrawler") Crawler lineCrawler,
            @Qualifier("NaverCrawler") Crawler naverCrawler,
            @Qualifier("TossCrawler") Crawler tossCrawler,
            @Qualifier("CoupangCrawler") Crawler coupangCrawler)
    {
        this.kakaoCrawler = kakaoCrawler;
        this.LineCrawler = lineCrawler;
        this.naverCrawler = naverCrawler;
        this.tossCrawler = tossCrawler;
        this.coupangCrawler = coupangCrawler;
    }

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<Void> crawlingKakao() {
        List<Job> crawling = kakaoCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "라인 채용 크롤링")
    @PostMapping("/line")
    public ResponseEntity<Void> crawlingLine() {
        List<Job> crawling = LineCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "네이버 채용 크롤링")
    @PostMapping("/naver")
    public ResponseEntity<Void> crawlingNaver() {
        List<Job> crawling = naverCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "쿠팡 채용 크롤링")
    @PostMapping("/coupang")
    public ResponseEntity<Void> crawlingCoupang() {
        List<Job> crawling = coupangCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "토스 채용 크롤링")
    @PostMapping("/toss")
    public ResponseEntity<Void> crawlingToss() {
        List<Job> crawling = tossCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }
}
