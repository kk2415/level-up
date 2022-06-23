package com.levelup.api.controller;


import com.levelup.api.service.VoteService;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<VoteResponse> create(@RequestBody @Validated CreateVoteRequest voteRequest,
                                 @AuthenticationPrincipal Member member) {
        System.out.println(voteRequest.getVoteType());
        System.out.println(voteRequest.getOwnerId());
        System.out.println(member.getId());

        VoteResponse voteResponse = voteService.create(voteRequest, member.getId());

        return ResponseEntity.ok().body(voteResponse);
    }

}
