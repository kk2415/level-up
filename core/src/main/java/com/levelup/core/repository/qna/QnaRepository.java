package com.levelup.core.repository.qna;


import com.levelup.core.domain.qna.Qna;
import com.levelup.core.dto.post.PostSearch;

import java.util.List;


public interface QnaRepository {

    void save(Qna notice);
    Qna findById(Long id);
    List<Qna> findAll(int page, PostSearch postSearch);
    List<Qna> findByMemberId(Long memberId);
    List<Qna> findAll();
    Qna findNextPage(Long id);
    Qna findPrevPage(Long id);
    void delete(Long id);

}
