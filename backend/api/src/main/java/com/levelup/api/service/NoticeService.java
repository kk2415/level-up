package com.levelup.api.service;


import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.Notice;
import com.levelup.core.dto.post.PostSearch;
import com.levelup.core.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberService memberService;

    /**
     * 생성
     * */
    @Transactional
    public Long create(Long memberId, String title, String writer ,String content) {
        Member findMember = memberService.findOne(memberId);

        Notice notice = Notice.createNotice(findMember, title, writer, content);
        noticeRepository.save(notice);

        return notice.getId();
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

    public List<Notice> findAll(Long page, PostSearch postSearch) {
        if (page == null && postSearch.getField() == null && postSearch.getQuery() == null) {
            return noticeRepository.findAll();
        }
        return noticeRepository.findAll(page, postSearch);
    }

    public Long count(Long page, PostSearch postSearch) {
        return noticeRepository.count(page, postSearch);
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
    public void update(Long id, String title, String content) {
        Notice findNotice = noticeRepository.findById(id);
        findNotice.change(title, content);
    }

    @Transactional
    public void addViews(Notice findNotice) {
        findNotice.addViews();
    }


    /**
     * 삭제
     * */
    @Transactional
    public void delete(Long id) {
        noticeRepository.delete(id);
    }

}
