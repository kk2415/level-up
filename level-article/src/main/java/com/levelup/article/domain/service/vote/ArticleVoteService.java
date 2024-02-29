package com.levelup.article.domain.service.vote;


import com.levelup.article.domain.entity.Article;
import com.levelup.article.domain.entity.ArticleVote;
import com.levelup.article.domain.repository.ArticleRepository;
import com.levelup.article.domain.repository.ArticleVoteRepository;
import com.levelup.article.domain.service.dto.VoteDto;
import com.levelup.common.exception.EntityDuplicationException;
import com.levelup.common.exception.ErrorCode;
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
        final List<ArticleVote> votes = articleVoteRepository.findByMemberIdAndArticleId(dto.getMemberId(), dto.getTargetId());

        if (isDuplicationVote(votes)) {
            articleVoteRepository.deleteAll(votes);
            return VoteDto.of(votes.get(0), false);
        }

        Article article = articleRepository.findById(dto.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        ArticleVote vote = dto.toEntity(article);
        articleVoteRepository.save(vote);

        return VoteDto.of(vote, true);
    }

    private boolean isDuplicationVote(List<ArticleVote> votes) {
        return !votes.isEmpty();
    }
}
