package com.levelup.api.controller.member;

import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.member.CreateMemberRequest;
import com.levelup.core.dto.member.CreateMemberResponse;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.dto.member.UpdateMemberRequest;
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

import javax.servlet.http.HttpServletRequest;
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


    /**
     * 생성
     * */
    @PostMapping("/member/image")
    public ResponseEntity createProfileImage(@ModelAttribute MultipartFile file) throws IOException {
        UploadFile profileImage = memberService.createProfileImage(file);

        return ResponseEntity.ok().body(profileImage);
    }


    /**
     * 조회
     * */
    @GetMapping("/member/{email}")
    public MemberResponse getMember(@PathVariable("email") String email) {
        return memberService.findByEmail(email);
    }

    @GetMapping("/members")
    public ResponseEntity<Result> getAllMembers() {
        List<MemberResponse> members = memberService.findAllMembers();

        return ResponseEntity.ok(new Result<>(members, members.size()));
    }

    @GetMapping(path = "/member/{email}/image", produces = "image/jpeg")
    public Resource getProfileImage(@PathVariable String email) throws MalformedURLException {
        return memberService.getProfileImage(email);
    }

    @GetMapping("/member")
    public ResponseEntity confirmLogin(@AuthenticationPrincipal Member member) {
        if (member == null) {
            throw new MemberNotFoundException("'해당하는 회원이 없습니다.");
        }

        return ResponseEntity.ok().body(new MemberResponse(member));
    }


    /**
     * 수정
     * */
    @PatchMapping("/member")
    public ResponseEntity modifyMember(@RequestBody UpdateMemberRequest updateMemberRequest,
                                       @AuthenticationPrincipal Member member) {
        memberService.modifyMember(updateMemberRequest, member.getId());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/member/{email}/image")
    public ResponseEntity<UploadFile> modifyMemberProfile(@PathVariable String email, MultipartFile file) throws IOException {
        Member member = memberRepository.findByEmail(email);

        UploadFile profileImage = memberService.modifyProfileImage(file, member.getId());

        return ResponseEntity.ok().body(profileImage);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/member/{memberId}")
    public void delete(@PathVariable Long memberId) throws IOException {
        memberService.delete(memberId);
    }

}
