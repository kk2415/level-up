package com.levelup.recruit.controller.v1.dto;

import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class JobDto {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Request {

        @NotNull @NotBlank
        private String title;

        @NotNull @NotBlank
        private Company company;

        @NotNull
        private String url;

        @NotNull @NotBlank
        private OpenStatus openStatus;

        @NotNull
        private String noticeEndDate;

        public static Request of(String title, Company company, String url, OpenStatus openStatus, String noticeEndDate) {
            return new Request(title, company, url, openStatus, noticeEndDate);
        }

        public Job toDomain() {
            return Job.of(title, company, url, openStatus, noticeEndDate, LocalDateTime.now());
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Response {

        protected Long id;
        private String title;
        private Company company;
        private String url;
        private OpenStatus openStatus;
        private String noticeEndDate;
        private LocalDateTime createdAt;

        public static Response from(Job job) {
            return new Response(
                    job.getId(),
                    job.getTitle(),
                    job.getCompany(),
                    job.getUrl(),
                    job.getOpenStatus(),
                    job.getNoticeEndDate(),
                    job.getCreatedAt());
        }
    }
}
