package com.levelup.channel.domain.service.vote;

import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelArticleVote;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.vote.ChannelArticleVoteRepository;
import com.levelup.channel.domain.service.dto.ChannelVoteDto;
import com.levelup.common.exception.EntityDuplicationException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelArticleVoteService implements ChannelVoteService {

    private final ChannelArticleRepository articleRepository;
    private final ChannelArticleVoteRepository articleVoteRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public ChannelVoteDto save(ChannelVoteDto dto) {
        final ChannelArticle article = articleRepository.findById(dto.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));
        final ChannelMember channelMember = channelMemberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_MEMBER_NOT_FOUND));
        final List<ChannelArticleVote> votes
                = articleVoteRepository.findByChannelMemberIdAndArticleId(dto.getMemberId(), dto.getTargetId());

        if (isDuplicationVote(votes)) {
            articleVoteRepository.deleteAll(votes);
            return ChannelVoteDto.of(votes.get(0), false);
        }

        ChannelArticleVote vote = dto.toEntity(channelMember, article);
        articleVoteRepository.save(vote);

        return ChannelVoteDto.of(vote, true);
    }

    private boolean isDuplicationVote(List<ChannelArticleVote> votes) {
        return !votes.isEmpty();
    }
}
