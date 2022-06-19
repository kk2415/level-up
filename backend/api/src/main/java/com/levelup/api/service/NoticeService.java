package com.levelup.api.service;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.file.LocalFileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.Notice;
import com.levelup.core.dto.notice.NoticeRequest;
import com.levelup.core.dto.notice.NoticeResponse;
import com.levelup.core.dto.post.SearchCondition;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.notice.NoticeRepository;
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
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final LocalFileStore fileStore;


    /**
     * 생성
     * */
    @Transactional
    public NoticeResponse create(NoticeRequest noticeRequest, Long memberId) {
        Member findMember = memberRepository.findById(memberId);

        Notice notice = noticeRequest.toEntity(findMember);
        noticeRepository.save(notice);

        return new NoticeResponse(notice.getId(), notice.getTitle(), notice.getWriter(), notice.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(notice.getCreatedDate()),
                notice.getViews(), (int) notice.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    public UploadFile createFileByMultiPart(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        return fileStore.storeFile(ImageType.NOTICE, file);
    }



    /**
     * 조회
     * */
    public NoticeResponse findById(Long id, String view) {
        Notice findNotice = noticeRepository.findById(id);

        if (view.equals("true")) {
            findNotice.addViews();
        }

        return new NoticeResponse(findNotice.getId(), findNotice.getTitle(), findNotice.getWriter(), findNotice.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findNotice.getCreatedDate()),
                findNotice.getViews(), (int) findNotice.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    public List<NoticeResponse> findAll() {
        return noticeRepository.findAll()
                .stream()
                .map(n -> new NoticeResponse(n.getId(), n.getTitle(), n.getWriter(), n.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(n.getCreatedDate()),
                        n.getViews(),
                        (int) n.getComments().stream().filter(c -> c.getParent() == null).count()))
                .collect(Collectors.toList());
    }

    public List<NoticeResponse> findAll(Long page, String field, String query) {
        if (page == null && field == null && query == null) {
            return findAll();
        }

        SearchCondition searchCondition = new SearchCondition(field, query);

        return noticeRepository.findAll(page, searchCondition)
                .stream()
                .map(n -> new NoticeResponse(n.getId(), n.getTitle(), n.getWriter(), n.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(n.getCreatedDate()),
                        n.getViews(),
                        (int) n.getComments().stream().filter(c -> c.getParent() == null).count()))
                .collect(Collectors.toList());
    }

    public List<Notice> findAll(Long page, SearchCondition postSearch) {
        if (page == null && postSearch.getField() == null && postSearch.getQuery() == null) {
            return noticeRepository.findAll();
        }
        return noticeRepository.findAll(page, postSearch);
    }

    public Long count(Long page, String field, String query) {
        SearchCondition searchCondition = new SearchCondition(field, query);

        return noticeRepository.count(page, searchCondition);
    }

    public NoticeResponse findNextPage(Long id) {
        Notice nextPage = noticeRepository.findNextPage(id);

        return new NoticeResponse(nextPage.getId(), nextPage.getTitle(), nextPage.getWriter(), nextPage.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(nextPage.getCreatedDate()),
                nextPage.getViews(), (int) nextPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    public NoticeResponse findPrevPage(Long id) {
        Notice prevPage = noticeRepository.findPrevPage(id);

        return new NoticeResponse(prevPage.getId(), prevPage.getTitle(), prevPage.getWriter(), prevPage.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(prevPage.getCreatedDate()),
                prevPage.getViews(), (int) prevPage.getComments().stream().filter(c -> c.getParent() == null).count());
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
