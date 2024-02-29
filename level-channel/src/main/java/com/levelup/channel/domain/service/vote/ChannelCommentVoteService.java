package com.levelup.channel.domain.service.vote;

import com.levelup.channel.domain.entity.ChannelComment;
import com.levelup.channel.domain.entity.ChannelCommentVote;
import com.levelup.channel.domain.entity.ChannelMember;
import com.levelup.channel.domain.service.dto.ChannelVoteDto;
import com.levelup.channel.domain.repository.channel.ChannelMemberRepository;
import com.levelup.channel.domain.repository.comment.ChannelCommentRepository;
import com.levelup.channel.domain.repository.vote.ChannelCommentVoteRepository;
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
public class ChannelCommentVoteService implements ChannelVoteService {

    private final ChannelCommentRepository commentRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final ChannelCommentVoteRepository commentVoteRepository;

    public ChannelVoteDto save(ChannelVoteDto dto) {
        final ChannelComment comment = commentRepository.findById(dto.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ARTICLE_NOT_FOUND));
        final ChannelMember channelMember = channelMemberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHANNEL_MEMBER_NOT_FOUND));
        final List<ChannelCommentVote> votes
                = commentVoteRepository.findByChannelMemberIdAndCommentId(dto.getMemberId(), dto.getTargetId());

        if (isDuplicationVote(votes)) {
            commentVoteRepository.deleteAll(votes);
            return ChannelVoteDto.of(votes.get(0), false);
        }

        ChannelCommentVote vote = dto.toEntity(channelMember, comment);
        commentVoteRepository.save(vote);

        return ChannelVoteDto.of(vote, true);
    }

    public boolean isDuplicationVote(List<ChannelCommentVote> votes) {
        return !votes.isEmpty();
    }
}
