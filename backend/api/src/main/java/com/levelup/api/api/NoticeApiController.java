package com.levelup.api.api;


import com.levelup.api.service.NoticeService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.notice.NoticeRequest;
import com.levelup.core.dto.notice.NoticeResponse;
import com.levelup.core.dto.notice.UpdateNoticeRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "공지사항 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeApiController {

    private final NoticeService noticeService;


    /**
     * 생성
     * */
    @PostMapping("/notice")
    public ResponseEntity<NoticeResponse> create(@RequestBody @Validated NoticeRequest noticeRequest,
                                 @AuthenticationPrincipal Long memberId) {
        NoticeResponse findNotice = noticeService.create(noticeRequest, memberId);

        return ResponseEntity.ok().body(findNotice);
    }

    @PostMapping("/notice/file")
    public ResponseEntity<UploadFile> storeFile(MultipartFile file) throws IOException {
        UploadFile uploadFiles = noticeService.createFileByMultiPart(file);

        return ResponseEntity.ok().body(uploadFiles);
    }


    /**
     * 조회
     * */
    @GetMapping("/notices")
    public ResponseEntity notices(@RequestParam(required = false) Long page,
                          @RequestParam(required = false) String field,
                          @RequestParam(required = false) String query) {
        List<NoticeResponse> notices = noticeService.findAll(page, field, query);

        return ResponseEntity.ok().body(new Result(notices, notices.size()));
    }

    @GetMapping("/notice/{noticeId}")
    public ResponseEntity notice(@PathVariable Long noticeId,
                                 @RequestParam(required = false, defaultValue = "false") String view) {
        NoticeResponse findNotice = noticeService.findById(noticeId, view);

        return ResponseEntity.ok().body(findNotice);
    }

    @GetMapping("/notice/{noticeId}/next")
    public ResponseEntity findNextPage(@PathVariable Long noticeId) {
        NoticeResponse nextPage = noticeService.findNextPage(noticeId);

        return ResponseEntity.ok().body(nextPage);
    }

    @GetMapping("/notice/{noticeId}/prev")
    public ResponseEntity findPrevPage(@PathVariable Long noticeId) {
        NoticeResponse prevPage = noticeService.findPrevPage(noticeId);

        return ResponseEntity.ok().body(prevPage);
    }

    @GetMapping("/notices/count")
    public ResponseEntity noticesCount(@RequestParam(required = false) Long page,
                                       @RequestParam(required = false) String field,
                                       @RequestParam(required = false) String query) {
        Long count = noticeService.count(page, field, query);

        return ResponseEntity.ok().body(new Result(count, 1));
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
