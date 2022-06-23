package com.levelup.api.service;


import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;
import com.levelup.core.exception.DuplicateVoteException;
import com.levelup.core.exception.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;


    /**
     * 생성
     * */
    public VoteResponse create(CreateVoteRequest voteRequest, Long memberId) {
        validDuplicateVote(voteRequest.getVoteType(), voteRequest.getOwnerId(), memberId);

        Member findMember = memberRepository.findById(memberId);

        Vote vote = Vote.createVote(findMember);
        associationMapping(vote, voteRequest.getOwnerId(),voteRequest.getVoteType());
        voteRepository.save(vote);

        return new VoteResponse(voteRequest.getOwnerId(), voteRequest.getVoteType());
    }

    public void associationMapping(Vote vote, Long ownerId, VoteType voteType) {
        switch (voteType) {
            case ARTICLE:
                Article article = articleRepository.findById(ownerId)
                        .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
                article.addVote(vote);
                break;
            case COMMENT:
                Comment comment = commentRepository.findById(ownerId);
                comment.addVote(vote);
                break;
        }
    }

    private void validDuplicateVote(VoteType voteType, Long ownerId, Long memberId) {
        List<Vote> votes = new ArrayList<>();

        switch (voteType) {
            case ARTICLE:
                votes = voteRepository.findByMemberIdAndArticleId(ownerId, memberId).orElse(new ArrayList<>());
                break;
            case COMMENT:
                votes = voteRepository.findByMemberIdAndCommentId(ownerId, memberId).orElse(new ArrayList<>());
                break;
        }

        if (!votes.isEmpty()) {
            throw new DuplicateVoteException("추천은 한 번만 가능합니다");
        }
    }


    /**
     * 조회
     * */
    public Vote findById(Long id) {
        return voteRepository.findById(id);
    }


    /**
     * 삭제
     * */
    public void delete(Long id) {
        voteRepository.delete(id);
    }


}
