package com.levelup.api.service;

import com.levelup.api.config.DateFormat;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.file.FileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.ChannelNotice;
import com.levelup.core.dto.channelNotice.ChannelNoticeRequest;
import com.levelup.core.dto.channelNotice.ChannelNoticeResponse;
import com.levelup.core.dto.channelNotice.PagingChannelNoticeResponse;
import com.levelup.core.exception.AuthorizationException;
import com.levelup.core.repository.channel.ChannelRepository;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.notice.ChannelNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelNoticeService {

    private final ChannelNoticeRepository channelNoticeRepository;
    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;


    /**
     * 생성
     * */
    public ChannelNoticeResponse create(ChannelNoticeRequest noticeRequest, Long channelId, Long memberId) {
        Long managerId = channelRepository.findById(channelId).getManager().getId();
        Member member = memberRepository.findById(memberId);

        if (memberId.equals(managerId)) {
            Channel findChannel = channelRepository.findById(channelId);

            ChannelNotice channelNotice = ChannelNotice.createChannelNotice(findChannel, noticeRequest.getTitle(),
                    member.getName(), noticeRequest.getContent());
            channelNoticeRepository.save(channelNotice);

            return new ChannelNoticeResponse(channelNotice.getId(),
                    channelNotice.getChannel().getManager().getId(), channelNotice.getTitle(),
                    channelNotice.getWriter(), channelNotice.getContent(), channelNotice.getViews(), 0L,
                    DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channelNotice.getCreatedDate()),
                    channelNotice.getComments().size());
        }

        throw new AuthorizationException("권한이 없습니다.");
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(ImageType.CHANNEL_NOTICE, file);
    }


    /**
     * 조회
     * */
    public ChannelNoticeResponse getChannelNotice(Long id, String view) {
        ChannelNotice findNotice = channelNoticeRepository.findById(id);

        if (view.equals("true")) {
            findNotice.addViews();
        }

        return new ChannelNoticeResponse(id, findNotice.getChannel().getManager().getId(), findNotice.getTitle(),
                findNotice.getWriter(), findNotice.getContent(), findNotice.getViews(), 0L,
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findNotice.getCreatedDate()),
                (int)findNotice.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    public List<PagingChannelNoticeResponse> getChannelNotices(Long channelId, int page) {
        List<ChannelNotice> findNotices = channelNoticeRepository.findByChannelId(channelId, page);
        int noticeCount = channelNoticeRepository.findByChannelId(channelId).size();

        return findNotices.stream()
                .map(n -> new PagingChannelNoticeResponse(n.getId(), n.getTitle(), n.getWriter(), n.getContent(), n.getViews(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(n.getCreatedDate()),
                        (int)n.getComments().stream().filter(c -> c.getParent() == null).count(),
                        noticeCount))
                .collect(Collectors.toList());
    }

    public List<ChannelNotice> findAll() {
        return channelNoticeRepository.findAll();
    }

    public ChannelNoticeResponse findNextPage(Long id) {
        ChannelNotice nextPage = channelNoticeRepository.findNextPage(id);

        return new ChannelNoticeResponse(nextPage.getId(), nextPage.getChannel().getManager().getId(),
                nextPage.getTitle(), nextPage.getWriter(), nextPage.getContent(), nextPage.getViews(), 0L,
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(nextPage.getCreatedDate()),
                (int)nextPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    public ChannelNoticeResponse findPrevPage(Long id) {
        ChannelNotice prevPage = channelNoticeRepository.findPrevPage(id);

        return new ChannelNoticeResponse(prevPage.getId(), prevPage.getChannel().getManager().getId(),
                prevPage.getTitle(),
                prevPage.getWriter(), prevPage.getContent(), prevPage.getViews(), 0L,
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(prevPage.getCreatedDate()),
                (int)prevPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }


    /**
     * 수정
     * */
    public ChannelNoticeResponse modifyChannelNotice(ChannelNoticeRequest noticeRequest, Long channelNoticeId, Long channelId,
                                    Long memberId) {
        Long managerId = channelRepository.findById(channelId).getManager().getId();

        if (memberId.equals(managerId)) {
            ChannelNotice notice = channelNoticeRepository.findById(channelNoticeId);
            notice.change(noticeRequest.getTitle(), noticeRequest.getContent());

            return new ChannelNoticeResponse(notice.getId(), notice.getChannel().getManager().getId(),
                    notice.getTitle(),
                    notice.getWriter(), notice.getContent(), notice.getViews(), 0L,
                    DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(notice.getCreatedDate()),
                    (int)notice.getComments().stream().filter(c -> c.getParent() == null).count());
        }

        throw new AuthorizationException("권한이 없습니다.");
    }


    /**
     * 삭제
     * */
    public void delete(Long channelNoticeId, Long channelId, Long memberId) {
        Long managerId = channelRepository.findById(channelId).getManager().getId();

        if (!memberId.equals(managerId)) {
            throw new AuthorizationException("권한이 없습니다.");
        }

        channelNoticeRepository.delete(channelNoticeId);
    }

    public void deleteAll(List<Long> ids) {
        channelNoticeRepository.deleteAll(ids);
    }

}
