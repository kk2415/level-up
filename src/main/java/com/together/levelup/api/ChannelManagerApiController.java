package com.together.levelup.api;

import com.together.levelup.api.ChannelApiController.ManagerPostResponse;
import com.together.levelup.domain.channel.Channel;
import com.together.levelup.domain.channel.ChannelInfo;
import com.together.levelup.domain.member.Member;
import com.together.levelup.domain.post.Post;
import com.together.levelup.dto.channel.ManagerResponse;
import com.together.levelup.dto.member.MemberResponse;
import com.together.levelup.dto.post.PostSearch;
import com.together.levelup.exception.NotLoggedInException;
import com.together.levelup.service.ChannelService;
import com.together.levelup.service.MemberService;
import com.together.levelup.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelManagerApiController {

    private final ChannelService channelService;
    private final PostService postService;
    private final MemberService memberService;


    /**
     * 조회
     * */
    @GetMapping("/channel/{channelId}/manager")
    public ManagerResponse channel(@PathVariable Long channelId, HttpServletRequest request,
                                   @SessionAttribute(name = SessionName.SESSION_NAME, required = false) Member member) {
        Channel channel = channelService.findOne(channelId);
        List<Member> waitingMembers = memberService.findWaitingMemberByChannelId(channelId);
        List<Member> members = memberService.findByChannelId(channelId);
        List<Post> posts = postService.findByChannelId(channelId, new PostSearch(null, null));

        ChannelInfo channelInfo = new ChannelInfo(channel.getName(), member.getName(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channel.getDateCreated()),
                channel.getMemberCount(), (long)waitingMembers.size(), (long) posts.size(),
                channel.getThumbnailImage().getStoreFileName());

        List<MemberResponse> waitingMemberResponses = waitingMembers.stream().map(m -> new MemberResponse(
                m.getEmail(), m.getName(), m.getGender(),
                m.getBirthday(), m.getPhone(), m.getUploadFile()))
                .collect(Collectors.toList());

        List<MemberResponse> memberResponses = members.stream().map(m -> new MemberResponse(
                m.getEmail(), m.getName(), m.getGender(),
                m.getBirthday(), m.getPhone(), m.getUploadFile()))
                .collect(Collectors.toList());

        List<ManagerPostResponse> postResponses = posts.stream().map(p -> new ManagerPostResponse(p.getId(),
                p.getTitle(), p.getWriter())).collect(Collectors.toList());

        if (channel.getMember().getId().equals(member.getId())) {
            return new ManagerResponse(channelInfo, waitingMemberResponses, memberResponses, postResponses);
        }
        throw new NotLoggedInException("미인증 사용자");
    }

    /**
     * 수정
     * */


    /**
     * 삭제
     * */
}
