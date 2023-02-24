package com.levelup.recruit.controller.v1;

import com.levelup.notification.domain.constant.NotificationTemplateType;
import com.levelup.notification.domain.constant.NotificationType;
import com.levelup.notification.domain.domain.Notification;
import com.levelup.notification.domain.domain.NotificationTemplate;
import com.levelup.notification.domain.entity.NotificationTemplateEntity;
import com.levelup.notification.domain.service.NotificationService;
import com.levelup.recruit.controller.v1.dto.JobDto.Request;
import com.levelup.recruit.controller.v1.dto.JobDto.Response;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.JobFilterCondition;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import com.levelup.recruit.domain.enumeration.OrderBy;
import com.levelup.recruit.domain.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "채용 공고 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
@RestController
public class JobApiController {

    private final JobService jobService;
    private final NotificationService notificationService;

    @GetMapping("/test")
    public void test() {
        NotificationTemplateEntity notificationTemplateEntity = NotificationTemplateEntity.of(1L, "test push alram title", "test push alram body");
        NotificationTemplate notificationTemplate = NotificationTemplate.from(notificationTemplateEntity);

        notificationService.push(Notification.of(
                1L,
                1L,
                NotificationType.ANDROID,
                NotificationTemplateType.NEW_JOB,
                notificationTemplate));
    }

    @Operation(summary = "채용 공고 생성")
    @PostMapping("")
    public ResponseEntity<Response> create(@RequestBody @Valid Request request) {
        Job saveJob = jobService.save(request.toDomain());

        return ResponseEntity.ok().body(Response.from(saveJob));
    }

    @Operation(summary = "채용 공고 페이징 조회")
    @GetMapping("")
    public ResponseEntity<List<Response>> gets(
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) OpenStatus openStatus,
            @RequestParam(defaultValue = "CREATED_AT") OrderBy orderBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page
    ) {
        List<Job> jobs = jobService.filtering(JobFilterCondition.of(company, openStatus), orderBy, PageRequest.of(page, size));

        return ResponseEntity.ok().body(jobs.stream()
                .map(Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    @GetMapping("/today")
    public ResponseEntity<List<Response>> getCreatedToday(
            @RequestParam(required = false) Company company,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Job> jobs = jobService.getCreatedTodayByCompany(company, page, size);

        return ResponseEntity.ok().body(jobs.stream()
                .map(Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Operation(summary = "채용 공고 수정")
    @PatchMapping("")
    public ResponseEntity<Void> update(
            @RequestParam Long jobId,
            @RequestBody @Valid Request request
    ) {
        jobService.update(jobId, request.toDomain());

        return ResponseEntity.ok().build();
    }
}
