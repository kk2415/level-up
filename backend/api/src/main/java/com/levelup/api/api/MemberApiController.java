package com.levelup.api.api;

import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.LocalFileStore;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.member.CreateMemberRequest;
import com.levelup.core.dto.member.CreateMemberResponse;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.exception.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Tag(name = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LocalFileStore fileStore;

    @GetMapping("/channel-manager-")
    public String channelManager() {
        return "channelManager";
    }


    /**
     * 생성
     * */
    @PostMapping("/member")
    public ResponseEntity create(@RequestBody @Valid CreateMemberRequest memberRequest) throws IOException {


        CreateMemberResponse response = memberService.create(memberRequest);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/member/image")
    public ResponseEntity createProfileImage(@ModelAttribute MultipartFile file) throws IOException {
        UploadFile profileImage = memberService.createProfileImage(file);
        return ResponseEntity.ok().body(profileImage);
    }


    /**
     * 조회
     * */
    @GetMapping("/members")
    public ResponseEntity<Result> getAllMembers() {
        List<MemberResponse> members = memberService.findAllMembers();

        return ResponseEntity.ok(new Result<>(members, members.size()));
    }

    @GetMapping("/member/{email}")
    public MemberResponse getMember(@PathVariable("email") String email) {
        return memberService.findByEmail(email);
    }

    @GetMapping(path = "/member/{email}/image", produces = "image/jpeg")
    public Resource getProfileImage(@PathVariable String email) throws MalformedURLException {
        return memberService.getProfileImage(email);
    }

    @GetMapping("/member")
    public MemberResponse confirmLogin(@AuthenticationPrincipal Long memberId) {
        if (memberId == null) {
            log.warn("@AuthenticationPrincipal 에러");
            throw new MemberNotFoundException("'해당하는 회원이 없습니다.");
        }

        Member member = memberService.findOne(memberId);
        return new MemberResponse(member.getId(), member.getEmail(), member.getName(),
                member.getGender(), member.getBirthday(), member.getPhone(), member.getProfileImage());
    }

    /**
     * 수정
     * */
    @PatchMapping("/member/{email}/image")
    public void modifyMemberProfile(@PathVariable String email, MultipartFile file) throws IOException {
        Member member = memberRepository.findByEmail(email);

        memberService.modifyProfileImage(file, member.getId());
    }

}
