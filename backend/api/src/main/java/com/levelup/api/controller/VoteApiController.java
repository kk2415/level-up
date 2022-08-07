package com.levelup.api.controller;

import com.levelup.api.service.vote.ArticleVoteService;
import com.levelup.api.service.vote.CommentVoteService;
import com.levelup.core.domain.vote.VoteType;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VoteApiController {

    private final ArticleVoteService articleVoteService;
    private final CommentVoteService commentVoteService;

    @PostMapping("/votes")
    public ResponseEntity<VoteResponse> create(@RequestBody @Validated CreateVoteRequest voteRequest) {
        VoteResponse response;

        if (voteRequest.getVoteType() == VoteType.ARTICLE) {
            response = articleVoteService.save(voteRequest);
        } else {
            response = commentVoteService.save(voteRequest);
        }

        return ResponseEntity.ok().body(response);
    }
}
