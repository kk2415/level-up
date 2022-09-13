package com.levelup.api.controller;

import com.levelup.api.dto.service.vote.VoteDto;
import com.levelup.api.service.vote.VoteService;
import com.levelup.api.dto.request.vote.VoteRequest;
import com.levelup.api.dto.response.vote.VoteResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "추천 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VoteApiController {

    private final VoteService voteServiceImpl;

    @PostMapping("/votes")
    public ResponseEntity<VoteResponse> create(@RequestBody @Validated VoteRequest request) {
        VoteDto dto = voteServiceImpl.save(request.toDto());

        return ResponseEntity.ok().body(VoteResponse.from(dto));
    }
}
