package com.levelup.recruit.controller.v1;

import com.levelup.recruit.domain.crawler.Crawler;
import com.levelup.recruit.domain.domain.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
@RestController
public class JobApiController {

    private final Crawler kakaoCrawler;

    @PostMapping("")
    public ResponseEntity<Void> create() {
        List<Job> crawling = kakaoCrawler.crawling();
        crawling.forEach(System.out::println);

        return ResponseEntity.ok().build();
    }
}
