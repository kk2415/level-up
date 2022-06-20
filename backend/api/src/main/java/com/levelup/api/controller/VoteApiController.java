package com.levelup.api.controller;


import com.levelup.api.service.VoteService;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "추천 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteApiController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity create(@RequestBody @Validated CreateVoteRequest voteRequest,
                                 @AuthenticationPrincipal Member member) {
        voteService.create(voteRequest.getIdentity(), voteRequest.getArticleId(), member.getId());

        return new ResponseEntity(new Result<>("추천되었습니다", 0), HttpStatus.CREATED);
    }

}
