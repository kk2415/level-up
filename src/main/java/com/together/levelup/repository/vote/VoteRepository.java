package com.together.levelup.repository.vote;

import com.together.levelup.domain.vote.Vote;

import java.util.List;


public interface VoteRepository {

    public void save(Vote notice);
    public Vote findById(Long id);
    public List<Vote> findByPostAndMember(Long psotId, Long memberId);
    public List<Vote> findByQnaAndMember(Long qnaId, Long memberId);
    public List<Vote> findByCommentAndMember(Long commentId, Long memberId);
    public List<Vote> findByPostId(Long postId);
    public List<Vote> findByQnaId(Long qnaId);
    public List<Vote> findAll();
    public void delete(Long id);

}
