package com.together.levelup.repository.notice;

import com.together.levelup.domain.notice.Notice;
import com.together.levelup.dto.post.PostSearch;

import java.util.List;


public interface NoticeRepository {

    public void save(Notice notice);
    public Notice findById(Long id);
    public List<Notice> findAll(int page, PostSearch postSearch);
    public List<Notice> findByMemberId(Long memberId);
    public List<Notice> findAll();
    public Notice findNextPage(Long id);
    public Notice findPrevPage(Long id);
    public void delete(Long id);

}
