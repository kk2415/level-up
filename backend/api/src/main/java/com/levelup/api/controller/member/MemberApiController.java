package com.levelup.api.controller.member;

import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.Result;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.dto.member.UpdateMemberRequest;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    /**
     * 생성
     * */
    @PostMapping("/member/image")
    public ResponseEntity<UploadFile> createProfileImage(@ModelAttribute MultipartFile file) throws IOException {
        UploadFile response = memberService.createProfileImage(file);

        return ResponseEntity.ok().body(response);
    }


    /**
     * 조회
     * */
    @GetMapping("/member/{email}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("email") String email) {
        MemberResponse response = memberService.getByEmail(email);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/members")
    public ResponseEntity<Result> getAllMembers() {
        List<MemberResponse> response = memberService.getAllMembers();

        return ResponseEntity.ok(new Result<>(response, response.size()));
    }

    @GetMapping(path = "/member/{email}/image", produces = "image/jpeg")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String email) throws MalformedURLException {
        UrlResource response = memberService.getProfileImage(email);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/member")
    public ResponseEntity<MemberResponse> confirmLogin(@AuthenticationPrincipal Member member) {
        if (member == null) {
            throw new MemberNotFoundException("'해당하는 회원이 없습니다.");
        }

        return ResponseEntity.ok().body(MemberResponse.from(member));
    }


    /**
     * 수정
     * */
    @PatchMapping("/member")
    public ResponseEntity<Void> modifyMember(@RequestBody UpdateMemberRequest updateMemberRequest,
                                             @AuthenticationPrincipal Member member) {
        memberService.modify(updateMemberRequest, member.getId());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/member/{email}/image")
    public ResponseEntity<UploadFile> modifyMemberProfile(@PathVariable String email, MultipartFile file) throws IOException {
        UploadFile profileImage = memberService.modifyProfileImage(file, email);

        return ResponseEntity.ok().body(profileImage);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Void> delete(@PathVariable Long memberId) throws IOException {
        memberService.delete(memberId);

        return ResponseEntity.ok().build();
    }
}
