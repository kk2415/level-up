package com.levelup.channel.domain.service.vote;

import com.levelup.channel.domain.entity.VoteType;
import com.levelup.channel.domain.service.dto.ChannelVoteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelVoteServiceImpl implements ChannelVoteService {

    private final ChannelArticleVoteService articleVoteService;
    private final ChannelCommentVoteService commentVoteService;

    public ChannelVoteDto save(ChannelVoteDto dto) {
        if (dto.getVoteType() == VoteType.ARTICLE) {
            return articleVoteService.save(dto);
        }
        return commentVoteService.save(dto);
    }
}
