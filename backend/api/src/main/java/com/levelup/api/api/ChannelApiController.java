package com.levelup.api.api;

import com.levelup.api.service.ChannelService;
import com.levelup.api.service.MemberService;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import com.levelup.core.domain.file.Base64Dto;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.channel.*;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.repository.channel.ChannelRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Tag(name = "채널 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelApiController {

    private final ChannelService channelService;
    private final ChannelRepository channelRepository;
    private final MemberService memberService;


    /**
     * 생성
     * */
    @PostMapping("/channel")
    public CreateChannelResponse create(@RequestBody @Validated ChannelRequest channelRequest) {
        return channelService.create(channelRequest);
    }

    @PostMapping("/channel/description/files/base64")
    public ResponseEntity storeDescriptionFilesByBase64(@RequestBody Base64Dto base64) throws IOException {
        channelService.createFileByBase64(base64);
        return ResponseEntity.ok().body("파일 생성 완료");
    }

    @PostMapping("/channel/thumbnail")
    public ResponseEntity createChannelThumbnail(MultipartFile file) throws IOException {
        UploadFile thumbnail = channelService.createChannelThumbnail(file);

        return ResponseEntity.ok().body(thumbnail);
    }

    @PostMapping("/channel/{channelId}/waiting-member")
    public ResponseEntity addWaitingMember(@PathVariable Long channelId, @AuthenticationPrincipal Long id) {
        channelService.addWaitingMember(channelId, id);
        return new ResponseEntity(new Result("멤버 등록 성공", 1), HttpStatus.CREATED);
    }

    @PostMapping("/channel/{channelId}/member/{email}")
    public ResponseEntity addMember(@PathVariable Long channelId,
                                    @PathVariable String email,
                                    @AuthenticationPrincipal Long id) {
        channelService.deleteWaitingMember(channelId, email);
        channelService.addMember(channelId, Long.valueOf(id));
        return new ResponseEntity(new Result<>("멤버 등록 성공", 1), HttpStatus.CREATED);
    }


    /**
     * 조회
     * */
    @GetMapping("/channels")
    public Result getAll() {
        List<ChannelResponse> channels = channelService.getAll();

        return new Result(channels, channels.size());
    }

    @GetMapping("/channel/{channelId}")
    public ChannelResponse getByChannelId(@PathVariable Long channelId) {
        return channelService.getById(channelId);
    }

    @GetMapping("/channel/{channelId}/description")
    public ChannelDescriptionResponse getChannelDescriptionInfo(@PathVariable Long channelId) {
        Channel findChannel = channelRepository.findById(channelId);

        return new ChannelDescriptionResponse(findChannel.getName(), findChannel.getDescription(), findChannel.getThumbnailDescription(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(findChannel.getDateCreated()),
                findChannel.getManagerName(), findChannel.getMemberCount(), findChannel.getLimitedMemberNumber(),
                findChannel.getCategory(), findChannel.getThumbnailImage());
    }

    @GetMapping("/channels/{category}")
    public Result getByCategory(@PathVariable ChannelCategory category) {
        List<ChannelResponse> findChannels = channelService.getByCategory(category);

        return new Result(findChannels, findChannels.size());
    }

    @GetMapping(path = "/channel/{channelId}/thumbnail", produces = "image/jpeg")
    public Resource findMemberProfile(@PathVariable Long channelId) throws MalformedURLException {
        return channelService.getThumbNailImage(channelId);
    }

    @GetMapping("/channel/{channelId}/members")
    public Result channelMembers(@PathVariable Long channelId,
                                 @RequestParam(required = false) Long page,
                                 @RequestParam(required = false) Long count) {
        List<MemberResponse> members = memberService.findByChannelId(channelId, page, 5L);

        return new Result(members, members.size());
    }

    @GetMapping("/channel/{channelId}/waiting-members")
    public Result channelWaitingMembers(@PathVariable Long channelId,
                                        @RequestParam(required = false) Long page,
                                        @RequestParam(required = false) Long count) {
        List<MemberResponse> waitingMembers = memberService.findWaitingMemberByChannelId(channelId, page);

        return new Result(waitingMembers, waitingMembers.size());
    }


    /**
     * 수정
     * */
    @PatchMapping("/channel/{channelId}")
    public ResponseEntity modifyDetailDescription(@PathVariable Long channelId,
                                                  @RequestBody @Validated UpdateChannelRequest channelRequest) {
        channelService.update(channelId, channelRequest.getName(), channelRequest.getLimitedMemberNumber(),
                channelRequest.getDescription(), channelRequest.getThumbnailDescription(), channelRequest.getThumbnailImage());

        ChannelResponse findChannel = channelService.getById(channelId);
        return ResponseEntity.ok().body(findChannel);
    }

    @PatchMapping("/channel/{channelId}/thumbnail")
    public ResponseEntity modifyChannelThumbnail(@PathVariable Long channelId,
                                                 MultipartFile file) throws IOException {
        UploadFile thumbNail = channelService.modifyChannelThumbNail(file, channelId);

        return ResponseEntity.ok().body(thumbNail);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/channel/{channelId}")
    public ResponseEntity deleteChannel(@PathVariable Long channelId) {
        channelService.deleteChannel(channelId);

        return new ResponseEntity(new Result("삭제 완료", 1), HttpStatus.OK);
    }

    @DeleteMapping("/channel/{channelId}/member/{email}")
    public ResponseEntity deleteMembers(@PathVariable Long channelId,
                                        @PathVariable String email) {
        channelService.deleteMember(channelId, email);

        return new ResponseEntity(new Result("채널 회원 삭제 완료", 1), HttpStatus.OK);
    }

    @DeleteMapping("/channel/{channelId}/waiting-member/{email}")
    public ResponseEntity deleteWaitingMembers(@PathVariable Long channelId,
                                               @PathVariable String email) {
        channelService.deleteWaitingMember(channelId, email);

        return new ResponseEntity(new Result("가입 대기 회원 삭제 완료", 1), HttpStatus.OK);
    }

}
