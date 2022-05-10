package com.together.levelup.api;

import com.together.levelup.domain.file.FileStore;
import com.together.levelup.domain.file.ImageType;
import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.dto.notice_channel.ChannelNoticeRequest;
import com.together.levelup.dto.notice_channel.ChannelNoticeResponse;
import com.together.levelup.dto.notice_channel.DeleteChannelNoticeRequest;
import com.together.levelup.dto.Result;
import com.together.levelup.dto.notice_channel.PagingChannelNoticeResponse;
import com.together.levelup.service.ChannelNoticeService;
import com.together.levelup.service.ChannelService;
import com.together.levelup.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(SessionName.SESSION_NAME) != null) {
            Member manager = channelService.findOne(channel).getMember();
            Member requestMember = (Member)session.getAttribute(SessionName.SESSION_NAME);

            if (requestMember.getId().equals(manager.getId())) {
                Long id = channelNoticeService.create(channel, noticeRequest.getTitle(), manager.getName(),
                        noticeRequest.getContent());

                ChannelNotice channelNotice = channelNoticeService.findById(id);

                for (UploadFile uploadFile : noticeRequest.getUploadFiles()) {
                    fileService.create(noticeRequest, uploadFile);
                }

                return new ResponseEntity(new ChannelNoticeResponse(channelNotice.getId(), channelNotice.getTitle(),
                        channelNotice.getWriter(), channelNotice.getContent(), channelNotice.getViews(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channelNotice.getDateCreated())
                , channelNotice.getComments().size()), HttpStatus.OK);
            }
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/channel-notice/files")
    public ResponseEntity storeFiles(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return new ResponseEntity("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        UploadFile uploadFiles = fileStore.storeFile(ImageType.CHANNEL_NOTICE, file);

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

        return new ChannelNoticeResponse(id, findNotice.getTitle(),
                findNotice.getWriter(), findNotice.getContent(), findNotice.getViews(),
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

        return new Result(noticeResponses, noticeResponses.size());
    }

    @GetMapping("/channel-notice/{id}/nextPost")
    public ChannelNoticeResponse findNextPost(@PathVariable Long id) {
        ChannelNotice nextPage = channelNoticeService.findNextPage(id);

        return new ChannelNoticeResponse(nextPage.getId(), nextPage.getTitle(),
                nextPage.getWriter(), nextPage.getContent(), nextPage.getViews(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(nextPage.getDateCreated()),
                (int)nextPage.getComments().stream().filter(c -> c.getParent() == null).count());
    }

    @GetMapping("/channel-notice/{id}/prevPost")
    public ChannelNoticeResponse findPrevPost(@PathVariable Long id) {
        ChannelNotice prevPage = channelNoticeService.findPrevPage(id);

        return new ChannelNoticeResponse(prevPage.getId(), prevPage.getTitle(),
                prevPage.getWriter(), prevPage.getContent(), prevPage.getViews(),
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
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session.getAttribute(SessionName.SESSION_NAME) != null) {
            Member manager = channelService.findOne(channel).getMember();
            Member requestMember = (Member)session.getAttribute(SessionName.SESSION_NAME);

            if (requestMember.getId().equals(manager.getId())) {
                channelNoticeService.upadte(id, noticeRequest.getTitle(), noticeRequest.getContent());

                return new ResponseEntity(new Result("채널 공지사항이 수정되었습니다.", 0), HttpStatus.CREATED);
            }
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel-notice")
    public ResponseEntity delete(@RequestParam Long channel,
                                 @RequestBody @Validated DeleteChannelNoticeRequest noticeRequest,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session.getAttribute(SessionName.SESSION_NAME) != null) {
            Member manager = channelService.findOne(channel).getMember();
            Member requestMember = (Member)session.getAttribute(SessionName.SESSION_NAME);

            if (requestMember.getId().equals(manager.getId())) {
                channelNoticeService.deleteAll(noticeRequest.getIds());

                return new ResponseEntity(new Result("삭제되었습니다", 0), HttpStatus.OK);
            }
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

}
