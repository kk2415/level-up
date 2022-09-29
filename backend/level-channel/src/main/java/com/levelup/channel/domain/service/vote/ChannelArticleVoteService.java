package com.levelup.channel.domain.service.vote;

import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelArticleVote;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.repository.article.ChannelArticleRepository;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.vote.ChannelArticleVoteRepository;
import com.levelup.channel.domain.service.dto.ChannelVoteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        final ChannelMember channelMember
                = channelMemberRepository.findByChannelIdAndMemberId(dto.getChannelId(), dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        final List<ChannelArticleVote> votes
                = articleVoteRepository.findByChannelMemberIdAndArticleId(channelMember.getId(), dto.getTargetId());

        if (isDuplicationVote(votes)) {
            articleVoteRepository.delete(votes.get(0));
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
