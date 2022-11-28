package com.levelup.recruit.domain.crawler;

import com.levelup.recruit.domain.domain.Job;

import java.util.List;

/**
 * Exception: http connect, html parsing 예외 발생 시 터트림
 *            html 태그 수정 등으로 크롤링이 안됐을 시 예외 메시지만 로깅
 * */
public interface Crawler {

    List<Job> crawling();
}
