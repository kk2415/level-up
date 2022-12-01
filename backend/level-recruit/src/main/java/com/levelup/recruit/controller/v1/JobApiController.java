package com.levelup.recruit.controller.v1;

import com.levelup.recruit.controller.v1.dto.JobDto.Request;
import com.levelup.recruit.controller.v1.dto.JobDto.Response;
import com.levelup.recruit.domain.domain.Job;
import com.levelup.recruit.domain.domain.JobFilterCondition;
import com.levelup.recruit.domain.enumeration.Company;
import com.levelup.recruit.domain.enumeration.OpenStatus;
import com.levelup.recruit.domain.enumeration.OrderBy;
import com.levelup.recruit.domain.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
@RestController
public class JobApiController {

    private final JobService jobService;

    @PostMapping("")
    public ResponseEntity<Response> create(@RequestBody @Valid Request request) {
        Job saveJob = jobService.save(request.toDomain());

        return ResponseEntity.ok().body(Response.from(saveJob));
    }

    @GetMapping("")
    public ResponseEntity<List<Response>> gets(
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) OpenStatus openStatus,
            @RequestParam(defaultValue = "CREATED_AT") OrderBy orderBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page)
    {
        List<Job> jobs = jobService.filtering(JobFilterCondition.of(company, openStatus), orderBy, PageRequest.of(page, size));

        return ResponseEntity.ok().body(jobs.stream()
                .map(Response::from)
                .collect(Collectors.toUnmodifiableList()));
    }

    @PatchMapping("")
    public ResponseEntity<Void> update(
            @RequestParam Long jobId,
            @RequestBody @Valid Request request)
    {
        jobService.update(jobId, request.toDomain());

        return ResponseEntity.ok().build();
    }
}
