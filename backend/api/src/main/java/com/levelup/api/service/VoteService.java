package com.levelup.api.service;


import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;
import com.levelup.core.exception.article.PostNotFoundException;
import com.levelup.core.exception.comment.CommentNotFoundException;
import com.levelup.core.exception.vote.DuplicateVoteException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.vote.VoteRepository;
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
public class VoteService {

    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;


    /**
     * 생성
     * */
    public VoteResponse save(CreateVoteRequest voteRequest) {
        validateVote(voteRequest.getMemberId(), voteRequest.getTargetId(), voteRequest.getVoteType());

        Vote vote = voteRequest.toEntity();
        voteRepository.save(vote);

        increaseVoteCount(voteRequest.getTargetId(), voteRequest.getVoteType());
        return VoteResponse.from(vote);
    }

    private void validateVote(Long memberId, Long targetId, VoteType voteType) {
        List<Vote> votes = voteRepository.findByMemberIdAndTargetIdAndVoteType(memberId, targetId, voteType);

        if (!votes.isEmpty()) {
            throw new DuplicateVoteException("추천은 한 번만 할 수 있습니다.");
        }
    }


    public void increaseVoteCount(Long targetId, VoteType voteType) {
        switch (voteType) {
            case ARTICLE:
                Article article = articleRepository.findById(targetId)
                        .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
                article.addVote();
                break;
            case COMMENT:
                Comment comment = commentRepository.findById(targetId)
                        .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
                comment.addVote();
                break;
        }
    }

    /**
     * 삭제
     * */
    public void delete(Long id) {
        final Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 추천 엔티티를 찾을 수 없습니다."));

        voteRepository.delete(vote);
    }
}
