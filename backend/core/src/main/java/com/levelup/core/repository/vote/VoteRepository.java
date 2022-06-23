package com.levelup.core.repository.vote;


import com.levelup.core.domain.vote.Vote;

import java.util.List;
import java.util.Optional;


public interface VoteRepository {

    void save(Vote notice);
    Vote findById(Long id);
    Optional<List<Vote>> findByMemberIdAndCommentId(Long commentId, Long memberId);
    Optional<List<Vote>> findByMemberIdAndArticleId(Long articleId, Long memberId);
    List<Vote> findAll();
    void delete(Long id);

}
