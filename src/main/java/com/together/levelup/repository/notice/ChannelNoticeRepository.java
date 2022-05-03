package com.together.levelup.repository.notice;

import com.together.levelup.domain.notice.ChannelNotice;

import java.util.List;


public interface ChannelNoticeRepository {

    public void save(ChannelNotice channelNotice);
    public void saveAll(List<ChannelNotice> channelNotices);

    public ChannelNotice findById(Long id);
    public List<ChannelNotice> findByChannelId(Long channelId, int page);
    public List<ChannelNotice> findByMemberId(Long memberId);
    public List<ChannelNotice> findAll();
    public ChannelNotice findNextPage(Long id);
    public ChannelNotice findPrevPage(Long id);

    public void delete(Long id);
    public void deleteAll(List<Long> ids);

}
