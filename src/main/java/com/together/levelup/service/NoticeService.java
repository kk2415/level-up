package com.together.levelup.service;

import com.together.levelup.domain.notice.Notice;
import com.together.levelup.dto.post.PostSearch;
import com.together.levelup.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 생성
     * */
    @Transactional
    public Long create(Long id) {
        Notice findNotice = noticeRepository.findById(id);
        noticeRepository.save(findNotice);
        return findNotice.getId();
    }


    /**
     * 조회
     * */
    public Notice findById(Long id) {
        return noticeRepository.findById(id);
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public List<Notice> findAll(int page, PostSearch postSearch) {
        return noticeRepository.findAll(page, postSearch);
    }

    public List<Notice> findByMemberId(Long memberId) {
        return noticeRepository.findByMemberId(memberId);
    }

    public Notice findNextPage(Long id) {
        return noticeRepository.findNextPage(id);
    }

    public Notice findPrevPage(Long id) {
        return noticeRepository.findPrevPage(id);
    }


    /**
     * 수정
     * */
    @Transactional
    public void upadte(Long id, String title, String content) {
        Notice findNotice = noticeRepository.findById(id);
        findNotice.change(title, content);
    }


    /**
     * 삭제
     * */
    @Transactional
    public void delete(Long id) {
        noticeRepository.delete(id);
    }

}
