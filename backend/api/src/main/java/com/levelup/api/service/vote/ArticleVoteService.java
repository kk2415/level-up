package com.levelup.api.service.vote;


import com.levelup.api.dto.service.vote.VoteDto;
import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.vote.ArticleVote;
import com.levelup.api.dto.request.vote.VoteRequest;
import com.levelup.api.dto.response.vote.VoteResponse;
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

    public VoteDto save(VoteDto dto) {
        if (validateDuplicationAndDelete(dto.getMemberId(), dto.getTargetId())) {
            Article article = articleRepository.findById(dto.getTargetId())
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

            ArticleVote vote = dto.toEntity(article);
            articleVoteRepository.save(vote);

            return VoteDto.of(vote, true);
        }

        return VoteDto.of(dto.getMemberId(), dto.getTargetId(), dto.getVoteType(), false);
    }

    private boolean validateDuplicationAndDelete(Long memberId, Long articleId) {
        final List<ArticleVote> votes = articleVoteRepository.findByMemberIdAndArticleId(memberId, articleId);

        if (!votes.isEmpty()) {
            articleVoteRepository.delete(votes.get(0));
            return false;
        }
        return true;
    }
}
