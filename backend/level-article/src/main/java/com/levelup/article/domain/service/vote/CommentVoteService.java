package com.levelup.article.domain.service.vote;

import com.levelup.article.domain.entity.Comment;
import com.levelup.article.domain.entity.CommentVote;
import com.levelup.article.domain.repository.CommentRepository;
import com.levelup.article.domain.repository.CommentVoteRepository;
import com.levelup.article.domain.service.dto.VoteDto;
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
public class CommentVoteService implements VoteService {

    private final CommentRepository commentRepository;
    private final CommentVoteRepository commentVoteRepository;

    public VoteDto save(VoteDto dto) {
        final List<CommentVote> votes = commentVoteRepository.findByMemberIdAndCommentId(dto.getMemberId(), dto.getTargetId());

        if (isDuplicationVote(votes)) {
            commentVoteRepository.deleteAll(votes);
            return VoteDto.of(votes.get(0), false);
        }

        Comment article = commentRepository.findById(dto.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        CommentVote vote = dto.toEntity(article);
        commentVoteRepository.save(vote);

        return VoteDto.of(vote, true);
    }

    public boolean isDuplicationVote(List<CommentVote> votes) {
        return !votes.isEmpty();
    }
}
