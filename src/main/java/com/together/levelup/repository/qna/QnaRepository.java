package com.together.levelup.repository.qna;

import com.together.levelup.domain.qna.Qna;
import com.together.levelup.dto.post.PostSearch;

import java.util.List;


public interface QnaRepository {

    public void save(Qna notice);
    public Qna findById(Long id);
    public List<Qna> findAll(int page, PostSearch postSearch);
    public List<Qna> findByMemberId(Long memberId);
    public List<Qna> findAll();
    public Qna findNextPage(Long id);
    public Qna findPrevPage(Long id);
    public void delete(Long id);

}
