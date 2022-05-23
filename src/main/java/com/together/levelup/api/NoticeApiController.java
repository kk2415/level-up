package com.together.levelup.api;

import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.file.ImageType;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.notice.Notice;
import com.together.levelup.dto.Result;
import com.together.levelup.dto.notice.NoticeRequest;
import com.together.levelup.dto.notice.NoticeResponse;
import com.together.levelup.dto.notice.UpdateNoticeRequest;
import com.together.levelup.dto.post.PostSearch;
import com.together.levelup.service.FileService;
import com.together.levelup.service.MemberService;
import com.together.levelup.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeApiController {

    private final NoticeService noticeService;
    private final MemberService memberService;
    private final FileService fileService;
    private final FileStore fileStore;

    /**
     * 생성
     * */
    @PostMapping("/notice")
    public ResponseEntity create(@RequestBody @Validated NoticeRequest noticeRequest,
                                 @AuthenticationPrincipal String id) {
        Member member = memberService.findOne(Long.valueOf(id));
        Long noticeId = noticeService.create(member.getId(), noticeRequest.getTitle(), member.getName(),
                noticeRequest.getContent());

        Notice findNotice = noticeService.findById(noticeId);
        for (UploadFile uploadFile : noticeRequest.getUploadFiles()) {
            fileService.create(findNotice, uploadFile);
        }

        return new ResponseEntity(new Result("공지사항 생성 성공", 1), HttpStatus.CREATED);
    }

    @PostMapping("/notice/file")
    public ResponseEntity storeFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return new ResponseEntity("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        UploadFile uploadFiles = fileStore.storeFile(ImageType.NOTICE, file);

        return new ResponseEntity(uploadFiles.getStoreFileName(), HttpStatus.OK);
    }


    /**
     * 조회
     * */
    @GetMapping("/notices")
    public Result notices(@RequestParam(required = false) Long page,
                          @RequestParam(required = false) String field,
                          @RequestParam(required = false) String query) {
        PostSearch postSearch = new PostSearch(field, query);

        List<Notice> notices = noticeService.findAll(page, postSearch);
        List<NoticeResponse> noticeResponses = notices.stream()
                .map(n -> new NoticeResponse(n.getId(), n.getTitle(), n.getWriter(), n.getContent(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(n.getDateCreated()),
                        n.getViews(),
                        (int) n.getComments().stream().filter(c -> c.getParent() == null).count()))
                .collect(Collectors.toList());

        return new Result(noticeResponses, noticeResponses.size());
    }

    @GetMapping("/notices/count")
    public Result noticesCount(@RequestParam(required = false) Long page,
                          @RequestParam(required = false) String field,
                          @RequestParam(required = false) String query) {
        PostSearch postSearch = new PostSearch(field, query);

        Long count = noticeService.count(page, postSearch);
        return new Result(count, 1);
    }

    @GetMapping("/notice/{noticeId}")
    public NoticeResponse notice(@PathVariable Long noticeId,
                                 @RequestParam(required = false, defaultValue = "false") String view) {
        Notice findNotice = noticeService.findById(noticeId);
        if (view.equals("true")) {
            noticeService.addViews(findNotice);
        }

        return new NoticeResponse(findNotice.getId(), findNotice.getTitle(), findNotice.getWriter(), findNotice.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findNotice.getDateCreated()),
                findNotice.getViews(), (int) findNotice.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    @GetMapping("/notice/{noticeId}/next")
    public NoticeResponse findNextPage(@PathVariable Long noticeId) {
        Notice nextPage = noticeService.findNextPage(noticeId);

        return new NoticeResponse(nextPage.getId(), nextPage.getTitle(), nextPage.getWriter(), nextPage.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(nextPage.getDateCreated()),
                nextPage.getViews(), (int) nextPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    @GetMapping("/notice/{noticeId}/prev")
    public NoticeResponse findPrevPage(@PathVariable Long noticeId) {
        Notice prevPage = noticeService.findPrevPage(noticeId);

        return new NoticeResponse(prevPage.getId(), prevPage.getTitle(), prevPage.getWriter(), prevPage.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(prevPage.getDateCreated()),
                prevPage.getViews(), (int) prevPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }


    /**
     * 수정
     * */
    @PatchMapping("/notice/{noticeId}")
    public ResponseEntity update(@PathVariable Long noticeId,
                                 @RequestBody @Validated UpdateNoticeRequest noticeRequest) {
        noticeService.update(noticeId, noticeRequest.getTitle(), noticeRequest.getContent());

        return new ResponseEntity(new Result("수정 성공", 1), HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/notice/{noticeId}")
    public ResponseEntity delete(@PathVariable Long noticeId) {
        noticeService.delete(noticeId);

        return new ResponseEntity(new Result("삭제 성공", 1), HttpStatus.OK);
    }

}
