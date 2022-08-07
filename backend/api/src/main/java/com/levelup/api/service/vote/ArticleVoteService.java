package com.levelup.api.service.vote;


import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.core.dto.vote.CreateVoteRequest;
import com.levelup.core.dto.vote.VoteResponse;
import com.levelup.core.exception.vote.DuplicateVoteException;
import com.levelup.core.repository.article.ArticleRepository;
import com.levelup.core.repository.vote.ArticleVoteRepository;
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
public class ArticleVoteService implements VoteService {

    private final ArticleRepository articleRepository;
    private final ArticleVoteRepository articleVoteRepository;

    public VoteResponse save(CreateVoteRequest voteRequest) {
        validate(voteRequest.getMemberId(), voteRequest.getTargetId());

        Article article = articleRepository.findById(voteRequest.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        ArticleVote vote = voteRequest.toEntity(article);
        articleVoteRepository.save(vote);

        return VoteResponse.from(vote);
    }

    public void validate(Long memberId, Long articleId) {
        final List<ArticleVote> votes = articleVoteRepository.findByMemberIdAndArticleId(memberId, articleId);

        if (!votes.isEmpty()) {
            throw new DuplicateVoteException("추천은 한 번만 할 수 있습니다.");
        }
    }

    public void delete(Long voteId) {
        final ArticleVote vote = articleVoteRepository.findById(voteId)
                .orElseThrow(() -> new EntityNotFoundException("해당 추천 엔티티를 찾을 수 없습니다."));

        articleVoteRepository.delete(vote);
    }
}
