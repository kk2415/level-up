package com.levelup.core.repository.notice;



import com.levelup.core.domain.notice.Notice;
import com.levelup.core.dto.post.PostSearch;

import java.util.List;


public interface NoticeRepository {

    void save(Notice notice);
    Notice findById(Long id);
    List<Notice> findAll(Long page, PostSearch postSearch);
    List<Notice> findByMemberId(Long memberId);
    List<Notice> findAll();
    Notice findNextPage(Long id);
    Notice findPrevPage(Long id);
    Long count(Long page, PostSearch postSearch);
    void delete(Long id);

}
