package com.together.levelup.service;

import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.repository.channel.ChannelRepository;
import com.together.levelup.repository.member.MemberRepository;
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
    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;

    /**
     * 생성
     * */
    @Transactional
    public Long create(Long channelId, Long memberId, String title, String writer, String content) {
        Channel findChannel = channelRepository.findById(channelId);
        Member findMember = memberRepository.findById(memberId);

        if (!findChannel.getManagerName().equals(findMember.getEmail())) {
            throw new MemberNotFoundException("매니저만 공지사항을 작성할 수 있습니다.");
        }

        ChannelNotice channelNotice = ChannelNotice.createChannelNotice(findChannel, title, writer, content);
        channelNoticeRepository.save(channelNotice);

        return channelNotice.getId();
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
