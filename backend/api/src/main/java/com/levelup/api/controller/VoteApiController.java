package com.levelup.api.controller;


import com.levelup.api.service.VoteService;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;
import com.levelup.core.dto.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VoteApiController {

    private final VoteService voteService;

    @PostMapping("/votes")
    public ResponseEntity<VoteResponse> create(@RequestBody @Validated CreateVoteRequest voteRequest) {
        VoteResponse response = voteService.save(voteRequest);

        return ResponseEntity.ok().body(response);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handle(SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
        String exceptionDir = e.getClass().getName();
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        exceptionResponse.setTimeStamp(LocalDateTime.now());
        exceptionResponse.setMessage("중복 추천할 수 없습니다");
        exceptionResponse.setException(exceptionDir.substring(exceptionDir.lastIndexOf(".") + 1));
        exceptionResponse.setPath(request.getRequestURI());

        log.error("[{}] - {}", exceptionDir, exceptionResponse.getMessage());
        return ResponseEntity.ok().body(exceptionResponse);
    }

}
