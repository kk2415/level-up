package com.levelup.api.api;

import com.levelup.api.service.ChannelNoticeService;
import com.levelup.api.service.ChannelService;
import com.levelup.api.service.FileService;
import com.levelup.core.domain.file.FileStore;
import com.levelup.core.domain.file.ImageType;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.notice.ChannelNotice;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.notice_channel.ChannelNoticeRequest;
import com.levelup.core.dto.notice_channel.ChannelNoticeResponse;
import com.levelup.core.dto.notice_channel.DeleteChannelNoticeRequest;
import com.levelup.core.dto.notice_channel.PagingChannelNoticeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "채널 공지사항 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelNoticeApiController {

    private final ChannelNoticeService channelNoticeService;
    private final ChannelService channelService;
    private final FileStore fileStore;
    private final FileService fileService;

    /**
     * 생성
     * */
    @PostMapping("/channel-notice")
    public ResponseEntity create(@RequestParam Long channel,
                                 @RequestBody @Validated ChannelNoticeRequest noticeRequest,
                                 @AuthenticationPrincipal Long memberId) {
        Member manager = channelService.findOne(channel).getMember();

        if (Long.valueOf(memberId).equals(manager.getId())) {
            Long id = channelNoticeService.create(channel, noticeRequest.getTitle(), manager.getName(),
                    noticeRequest.getContent());

            ChannelNotice channelNotice = channelNoticeService.findById(id);

            return new ResponseEntity(new ChannelNoticeResponse(channelNotice.getId(),
                    channelNotice.getChannel().getMember().getId(), channelNotice.getTitle(),
                    channelNotice.getWriter(), channelNotice.getContent(), channelNotice.getViews(), 0L,
                    DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channelNotice.getDateCreated()),
                    channelNotice.getComments().size()), HttpStatus.OK);
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/channel-notice/files")
    public ResponseEntity storeFiles(MultipartFile file) throws IOException {
        UploadFile uploadFiles = fileStore.storeFile(ImageType.CHANNEL_NOTICE, file);
        if (uploadFiles == null) {
            return new ResponseEntity("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String storeFileName = uploadFiles.getStoreFileName();
        return new ResponseEntity(storeFileName, HttpStatus.OK);
    }


    /**
     * 조회
     * */
    @GetMapping("/channel-notice/{id}")
    public ChannelNoticeResponse findbyId(@PathVariable Long id,
                                          @RequestParam(required = false, defaultValue = "false") String view) {
        ChannelNotice findNotice = channelNoticeService.findById(id);

        if (view.equals("true")) {
            channelNoticeService.addViews(findNotice);
        }

        return new ChannelNoticeResponse(id, findNotice.getChannel().getMember().getId(), findNotice.getTitle(),
                findNotice.getWriter(), findNotice.getContent(), findNotice.getViews(), 0L,
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findNotice.getDateCreated()),
                (int)findNotice.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    @GetMapping("/channel-notices")
    public Result findAll(@RequestParam Long channel,
                          @RequestParam int page) {
        List<ChannelNotice> findNotices = channelNoticeService.findByChannelId(channel, page);
        int noticeCount = channelNoticeService.findByChannelId(channel).size();

        List<PagingChannelNoticeResponse> noticeResponses = findNotices.stream()
                .map(n -> new PagingChannelNoticeResponse(n.getId(), n.getTitle(), n.getWriter(), n.getContent(), n.getViews(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(n.getDateCreated()),
                        (int)n.getComments().stream().filter(c -> c.getParent() == null).count(),
                        noticeCount))
                .collect(Collectors.toList());

        return new Result<>(noticeResponses, noticeResponses.size());
    }

    @GetMapping("/channel-notice/{id}/nextPost")
    public ChannelNoticeResponse findNextPost(@PathVariable Long id) {
        ChannelNotice nextPage = channelNoticeService.findNextPage(id);

        return new ChannelNoticeResponse(nextPage.getId(), nextPage.getChannel().getMember().getId(),
                nextPage.getTitle(), nextPage.getWriter(), nextPage.getContent(), nextPage.getViews(), 0L,
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(nextPage.getDateCreated()),
                (int)nextPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    @GetMapping("/channel-notice/{id}/prevPost")
    public ChannelNoticeResponse findPrevPost(@PathVariable Long id) {
        ChannelNotice prevPage = channelNoticeService.findPrevPage(id);

        return new ChannelNoticeResponse(prevPage.getId(), prevPage.getChannel().getMember().getId(),
                prevPage.getTitle(),
                prevPage.getWriter(), prevPage.getContent(), prevPage.getViews(), 0L,
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(prevPage.getDateCreated()),
                (int)prevPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }


    /**
     * 수정
     * */
    @PatchMapping("/channel-notice/{id}")
    public ResponseEntity update(@PathVariable Long id,
                                 @RequestParam Long channel,
                                 @RequestBody @Validated ChannelNoticeRequest noticeRequest,
                                 @AuthenticationPrincipal Long memberId) {
        Member manager = channelService.findOne(channel).getMember();

        if (memberId.equals(manager.getId())) {
            channelNoticeService.upadte(id, noticeRequest.getTitle(), noticeRequest.getContent());
            return new ResponseEntity(new Result("채널 공지사항이 수정되었습니다.", 0), HttpStatus.CREATED);
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel-notice")
    public ResponseEntity deleteAll(@RequestParam Long channel,
                                 @RequestBody @Validated DeleteChannelNoticeRequest noticeRequest,
                                 @AuthenticationPrincipal Long memberId) {
        Member manager = channelService.findOne(channel).getMember();

        if (Long.valueOf(memberId).equals(manager.getId())) {
            channelNoticeService.deleteAll(noticeRequest.getIds());
            return new ResponseEntity(new Result("삭제되었습니다", 0), HttpStatus.OK);
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/channel-notice/{channelNoticeId}")
    public ResponseEntity delet0e(@PathVariable Long channelNoticeId,
                                 @RequestParam Long channel,
                                 @AuthenticationPrincipal Long memberId) {
        Member manager = channelService.findOne(channel).getMember();

        if (memberId.equals(manager.getId())) {
            channelNoticeService.delete(channelNoticeId);
            return new ResponseEntity(new Result("삭제되었습니다", 0), HttpStatus.OK);
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

}
