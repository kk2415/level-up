package com.levelup.core.repository.notice;


import com.levelup.core.domain.notice.ChannelNotice;

import java.util.List;


public interface ChannelNoticeRepository {

    void save(ChannelNotice channelNotice);
    void saveAll(List<ChannelNotice> channelNotices);
    ChannelNotice findById(Long id);
    List<ChannelNotice> findByChannelId(Long channelId);
    List<ChannelNotice> findByChannelId(Long channelId, int page);
    List<ChannelNotice> findByMemberId(Long memberId);
    List<ChannelNotice> findAll();
    ChannelNotice findNextPage(Long id);
    ChannelNotice findPrevPage(Long id);
    void delete(Long id);
    void deleteAll(List<Long> ids);

}
