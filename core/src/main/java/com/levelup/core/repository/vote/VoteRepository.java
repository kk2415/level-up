package com.levelup.core.repository.vote;


import com.levelup.core.domain.vote.Vote;

import java.util.List;


public interface VoteRepository {

    void save(Vote notice);
    Vote findById(Long id);
    List<Vote> findByPostAndMember(Long psotId, Long memberId);
    List<Vote> findByQnaAndMember(Long qnaId, Long memberId);
    List<Vote> findByCommentAndMember(Long commentId, Long memberId);
    List<Vote> findByPostId(Long postId);
    List<Vote> findByQnaId(Long qnaId);
    List<Vote> findAll();
    void delete(Long id);

}
