package com.levelup.recruit.controller.v1;

import com.levelup.recruit.controller.v1.dto.JobDto;
import com.levelup.recruit.crawler.Crawler;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "채용 사이트 크롤링 API")
@RequestMapping("/api/v1/jobs/crawling")
@RestController
public class CrawlingApiController {

    private final Crawler kakaoCrawler;
    private final Crawler LineCrawler;
    private final Crawler naverCrawler;
    private final Crawler coupangCrawler;
    private final Crawler tossCrawler;

    private final JobService jobService;

    @Autowired
    public CrawlingApiController(
            @Qualifier("KakaoCrawler") Crawler kakaoCrawler,
            @Qualifier("LineCrawler") Crawler lineCrawler,
            @Qualifier("NaverCrawler") Crawler naverCrawler,
            @Qualifier("TossCrawler") Crawler tossCrawler,
            @Qualifier("CoupangCrawler") Crawler coupangCrawler,
            JobService jobService
    ) {
        this.kakaoCrawler = kakaoCrawler;
        this.LineCrawler = lineCrawler;
        this.naverCrawler = naverCrawler;
        this.tossCrawler = tossCrawler;
        this.coupangCrawler = coupangCrawler;
        this.jobService = jobService;
    }

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<List<JobDto.Response>> crawlingKakao() {
        List<Job> jobs = kakaoCrawler.crawling();

        List<Job> savedJobs = jobService.saveIfAbsent(jobs, Company.KAKAO);

        List<Job> notExistsJobs = jobService.getNotMatched(jobs, Company.KAKAO);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Operation(summary = "라인 채용 크롤링")
    @PostMapping("/line")
    public ResponseEntity<List<JobDto.Response>> crawlingLine() {
        List<Job> jobs = LineCrawler.crawling();

        List<Job> savedJobs = jobService.saveIfAbsent(jobs, Company.LINE);

        List<Job> notExistsJobs = jobService.getNotMatched(jobs, Company.LINE);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Operation(summary = "네이버 채용 크롤링")
    @PostMapping("/naver")
    public ResponseEntity<List<JobDto.Response>> crawlingNaver() {
        List<Job> jobs = naverCrawler.crawling();

        List<Job> savedJobs = jobService.saveIfAbsent(jobs, Company.NAVER);

        List<Job> notExistsJobs = jobService.getNotMatched(jobs, Company.NAVER);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Operation(summary = "쿠팡 채용 크롤링")
    @PostMapping("/coupang")
    public ResponseEntity<List<JobDto.Response>> crawlingCoupang() {
        List<Job> jobs = coupangCrawler.crawling();

        List<Job> savedJobs = jobService.saveIfAbsent(jobs, Company.COUPANG);

        List<Job> notExistsJobs = jobService.getNotMatched(jobs, Company.COUPANG);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Operation(summary = "토스 채용 크롤링")
    @PostMapping("/toss")
    public ResponseEntity<List<JobDto.Response>> crawlingToss() {
        List<Job> jobs = tossCrawler.crawling();

        List<Job> savedJobs = jobService.saveIfAbsent(jobs, Company.TOSS);

        List<Job> notExistsJobs = jobService.getNotMatched(jobs, Company.TOSS);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }
}
