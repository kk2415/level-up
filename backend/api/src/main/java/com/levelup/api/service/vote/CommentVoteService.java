package com.levelup.api.service.vote;


import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.vote.CommentVote;
import com.levelup.api.dto.vote.CreateVoteRequest;
import com.levelup.api.dto.vote.VoteResponse;
import com.levelup.core.exception.vote.DuplicateVoteException;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.vote.CommentVoteRepository;
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

    public VoteResponse save(CreateVoteRequest voteRequest) {
        validate(voteRequest.getMemberId(), voteRequest.getTargetId());

        Comment article = commentRepository.findById(voteRequest.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        CommentVote vote = voteRequest.toEntity(article);
        commentVoteRepository.save(vote);

        return VoteResponse.from(vote);
    }

    public void validate(Long memberId, Long commentId) {
        final List<CommentVote> votes = commentVoteRepository.findByMemberIdAndCommentId(memberId, commentId);

        if (!votes.isEmpty()) {
            throw new DuplicateVoteException("추천은 한 번만 할 수 있습니다.");
        }
    }

    public void delete(Long voteId) {
        final CommentVote vote = commentVoteRepository.findById(voteId)
                .orElseThrow(() -> new EntityNotFoundException("해당 추천 엔티티를 찾을 수 없습니다."));

        commentVoteRepository.delete(vote);
    }
}
