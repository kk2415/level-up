package com.together.levelup.service;

import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.repository.notice.ChannelNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelNoticeService {

    private final ChannelNoticeRepository channelNoticeRepository;

    /**
     * 생성
     * */
    @Transactional
    public Long create(Long id) {
        ChannelNotice findNotice = channelNoticeRepository.findById(id);
        channelNoticeRepository.save(findNotice);
        return findNotice.getId();
    }


    /**
     * 조회
     * */
    public ChannelNotice findById(Long id) {
        return channelNoticeRepository.findById(id);
    }

    public List<ChannelNotice> findByChannelId(Long channelId, int page) {
        return channelNoticeRepository.findByChannelId(channelId, page);
    }

    public List<ChannelNotice> findByMemberId(Long memberId) {
        return channelNoticeRepository.findByMemberId(memberId);
    }

    public List<ChannelNotice> findAll() {
        return channelNoticeRepository.findAll();
    }

    public ChannelNotice findNextPage(Long id) {
        return channelNoticeRepository.findNextPage(id);
    }

    public ChannelNotice findPrevPage(Long id) {
        return channelNoticeRepository.findPrevPage(id);
    }


    /**
     * 수정
     * */
    @Transactional
    public void upadte(Long id, String title, String content) {
        ChannelNotice findNotice = channelNoticeRepository.findById(id);
        findNotice.change(title, content);
    }


    /**
     * 삭제
     * */
    @Transactional
    public void delete(Long id) {
        channelNoticeRepository.delete(id);
    }

}
