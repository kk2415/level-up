package com.levelup.api.api;

import com.levelup.api.service.ChannelService;
import com.levelup.api.service.MemberService;
import com.levelup.api.service.PostService;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelInfo;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.post.Post;
import com.levelup.core.dto.channel.ManagerPostResponse;
import com.levelup.core.dto.channel.ManagerResponse;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.dto.post.SearchCondition;
import com.levelup.core.repository.channel.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "채널 매니저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelManagerApiController {

    private final ChannelService channelService;
    private final ChannelRepository channelRepository;
    private final PostService postService;
    private final MemberService memberService;


    /**
     * 조회
     * */
    @Operation(description = "채널 전체 정보(가입 회원, 게시글 등) 조회")
    @GetMapping("/channel/{channelId}/manager")
    public ManagerResponse channel(@PathVariable Long channelId,
                                   @AuthenticationPrincipal Long id) {
        Channel channel = channelRepository.findById(channelId);
        Member findMember = memberService.findOne(id);
        List<Member> waitingMembers = memberService.findWaitingMemberByChannelId(channelId);
        List<Member> members = memberService.findByChannelId(channelId);
        List<Post> posts = postService.findByChannelId(channelId, new SearchCondition(null, null));

        ChannelInfo channelInfo = new ChannelInfo(channel.getName(), findMember.getName(),
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

//        if (channel.getMember().getId().equals(id)) {
//        }
        return new ManagerResponse(channelInfo, waitingMemberResponses, memberResponses, postResponses);

//        throw new NotLoggedInException("미인증 사용자");
    }

    /**
     * 수정
     * */


    /**
     * 삭제
     * */
}
