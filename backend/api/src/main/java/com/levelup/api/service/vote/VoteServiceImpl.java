package com.levelup.api.service.vote;


import com.levelup.api.dto.service.vote.VoteDto;
import com.levelup.core.domain.vote.VoteType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final ArticleVoteService articleVoteService;
    private final CommentVoteService commentVoteService;

    public VoteDto save(VoteDto dto) {
        if (dto.getVoteType() == VoteType.ARTICLE) {
            return articleVoteService.save(dto);
        }
        return commentVoteService.save(dto);
    }
}
