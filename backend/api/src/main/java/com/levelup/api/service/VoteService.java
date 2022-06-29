package com.levelup.api.service;


import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.comment.Comment;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.vote.Vote;
import com.levelup.core.domain.vote.VoteType;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;
import com.levelup.core.exception.DuplicateVoteException;
import com.levelup.core.exception.ExceptionResponse;
import com.levelup.core.exception.NoPlaceChnnelException;
import com.levelup.core.exception.PostNotFoundException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.comment.CommentRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.FileSystemException;
import java.rmi.RemoteException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public VoteResponse create(CreateVoteRequest voteRequest) {
        System.out.println("getVoteType() : " + voteRequest.getVoteType());
        Vote vote = voteRequest.toEntity();

        System.out.println("getVoteType() : " + vote.getVoteType());
        voteRepository.save(vote);
        increaseVoteCount(voteRequest.getTargetId(), voteRequest.getVoteType());

        return new VoteResponse(vote);
    }

    public void increaseVoteCount(Long targetId, VoteType voteType) {
        switch (voteType) {
            case ARTICLE:
                Article article = articleRepository.findById(targetId)
                        .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
                article.addVote();
                break;
            case COMMENT:
                Comment comment = commentRepository.findById(targetId);
                comment.addVote();
                break;
        }
    }

//
//    private void validDuplicateVote(VoteType voteType, Long ownerId, Long memberId) {
//        List<Vote> votes = new ArrayList<>();
//
//        switch (voteType) {
//            case ARTICLE:
//                votes = voteRepository.findByMemberIdAndArticleId(ownerId, memberId).orElse(new ArrayList<>());
//                break;
//            case COMMENT:
//                votes = voteRepository.findByMemberIdAndCommentId(ownerId, memberId).orElse(new ArrayList<>());
//                break;
//        }
//
//        if (!votes.isEmpty()) {
//            throw new DuplicateVoteException("추천은 한 번만 가능합니다");
//        }
//    }


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
