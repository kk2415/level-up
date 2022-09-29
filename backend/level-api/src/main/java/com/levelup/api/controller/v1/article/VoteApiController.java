package com.levelup.api.controller.v1.article;

import com.levelup.article.domain.service.dto.VoteDto;
import com.levelup.article.domain.service.vote.VoteService;
import com.levelup.api.controller.v1.dto.request.article.VoteRequest;
import com.levelup.api.controller.v1.dto.response.article.VoteResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "게시글 추천 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteApiController {

    private final VoteService voteServiceImpl;

    @PostMapping({"", "/"})
    public ResponseEntity<VoteResponse> create(@Valid @RequestBody VoteRequest request) {
        VoteDto dto = voteServiceImpl.save(request.toDto());

        return ResponseEntity.ok().body(VoteResponse.from(dto));
    }
}
