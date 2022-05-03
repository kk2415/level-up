package com.together.levelup.api;

import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.dto.ChannelNoticeRequest;
import com.together.levelup.dto.ChannelNoticeResponse;
import com.together.levelup.dto.DeleteChannelNoticeRequest;
import com.together.levelup.dto.Result;
import com.together.levelup.service.ChannelNoticeService;
import com.together.levelup.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelNoticeApiController {

    private final ChannelNoticeService channelNoticeService;
    private final ChannelService channelService;

    /**
     * 생성
     * */
    @PostMapping("/channel-notice")
    public ResponseEntity create(@RequestParam Long channel,
                                 @RequestBody @Validated ChannelNoticeRequest noticeRequest,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session.getAttribute(SessionName.SESSION_NAME) != null) {
            Member manager = channelService.findOne(channel).getMember();
            Member requestMember = (Member)session.getAttribute(SessionName.SESSION_NAME);

            if (requestMember.getId().equals(manager.getId())) {
                channelNoticeService.create(channel, noticeRequest.getTitle(), noticeRequest.getWriter(),
                        noticeRequest.getContent());

                return new ResponseEntity("채널 공지사항이 생성되었습니다.", HttpStatus.CREATED);
            }
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }


    /**
     * 조회
     * */
    @GetMapping("/channel-notice/{id}")
    public ChannelNoticeResponse findbyId(@PathVariable Long id) {
        ChannelNotice findNotice = channelNoticeService.findById(id);

        return new ChannelNoticeResponse(id, findNotice.getTitle(),
                findNotice.getWriter(), findNotice.getContent(), findNotice.getViews(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findNotice.getDateCreated()));
    }

    @GetMapping("/channel-notices")
    public Result findAll(@RequestParam Long channel,
                          @RequestParam int page) {
        List<ChannelNotice> findNotices = channelNoticeService.findByChannelId(channel, page);

        List<ChannelNoticeResponse> noticeResponses = findNotices.stream()
                .map(n -> new ChannelNoticeResponse(n.getId(), n.getTitle(), n.getWriter(), n.getContent(), n.getViews(),
                        DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(n.getDateCreated())))
                .collect(Collectors.toList());

        return new Result(noticeResponses, noticeResponses.size());
    }

    @GetMapping("/channel-notices/{id}/nextPost")
    public ChannelNoticeResponse findNextPost(@PathVariable Long id) {
        ChannelNotice nextPage = channelNoticeService.findNextPage(id);

        return new ChannelNoticeResponse(id, nextPage.getTitle(),
                nextPage.getWriter(), nextPage.getContent(), nextPage.getViews(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(nextPage.getDateCreated()));
    }

    @GetMapping("/channel-notices/{id}/prevPost")
    public ChannelNoticeResponse findPrevPost(@PathVariable Long id) {
        ChannelNotice prevPage = channelNoticeService.findPrevPage(id);

        return new ChannelNoticeResponse(id, prevPage.getTitle(),
                prevPage.getWriter(), prevPage.getContent(), prevPage.getViews(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(prevPage.getDateCreated()));
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

                return new ResponseEntity("채널 공지사항이 수정되었습니다.", HttpStatus.CREATED);
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

                return new ResponseEntity("채널 공지사항이 삭제되었습니다.", HttpStatus.CREATED);
            }
        }

        return new ResponseEntity("로그인이 안되어있거나 매니저가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

}
