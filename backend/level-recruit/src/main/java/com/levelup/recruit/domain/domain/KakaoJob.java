package com.levelup.recruit.domain.domain;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.entity.enumeration.ClosingType;
import com.levelup.recruit.domain.entity.enumeration.Company;
import com.levelup.recruit.domain.entity.enumeration.JobStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class KakaoJob extends Job {

    /*카카오 공고는 상시 채용일 경우 마김 일자가 "영입종료시" 으로 되어있다.*/
    private static String INFINITE_CLOSING_TYPE = "영입종료시";

    /*마감기한이 없는 경우 종료 일자를 2099년 12월 30일로 설정*/
    private static LocalDateTime INFINITE_CLOSING_DATE
            = LocalDateTime.of(LocalDate.of(2099, 12, 30), LocalTime.MAX);

    private KakaoJob(
            String title,
            Company company,
            String url,
            JobStatus jobStatus,
            ClosingType closingType,
            LocalDateTime openDate,
            LocalDateTime closingDate)
    {
        super(null, title, company, url, jobStatus, closingType, openDate, closingDate);
    }

    private KakaoJob(
            String title,
            String url,
            String rawClosingDate)
    {
        super(null,
                title,
                Company.KAKAO,
                url,
                JobStatus.TODAY,
                ClosingType.DEAD_LINE,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                INFINITE_CLOSING_DATE);

        ClosingType closingType = matchClosingType(rawClosingDate);
        this.closingType = closingType;

        if (ClosingType.DEAD_LINE.equals(closingType)) {
            this.closingDate = parseClosingDate(rawClosingDate);
        }
    }

    public static KakaoJob of(
            String title,
            String url,
            String rawClosingDate)
    {
        return new KakaoJob(title, url, rawClosingDate);
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, jobStatus, closingType, openDate, closingDate);
    }

    /**
     * 채용 공고 마감 방식이 상시 채용인지 마감 기한이 있는지 체크
     * */
    public ClosingType matchClosingType(String closingDate) {
        if (closingDate.equals(INFINITE_CLOSING_TYPE)) {
            return ClosingType.INFINITE;
        }
        return ClosingType.DEAD_LINE;
    }

    /**
     * @param rawClosingDate ex)"2022년 12월 24일 까지"
     * @return LocalDateTime 파라미터로 넘어온 rawClosingDate 문자열을 LocalDateTime 으로 파싱한 결과 값
     * */
    public LocalDateTime parseClosingDate(String rawClosingDate) {
        if (rawClosingDate.isEmpty() || rawClosingDate.isBlank()) {
            return INFINITE_CLOSING_DATE;
        }

        String newClosing = rawClosingDate.substring(0, rawClosingDate.length() - 3);
        LocalDate date = LocalDate.parse(newClosing, DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

        return LocalDateTime.of(date, LocalTime.MAX);
    }
}
