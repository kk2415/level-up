package com.levelup.api.controller.member;

import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.member.MemberResponse;
import com.levelup.core.dto.member.UpdateMemberRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;

@Tag(name = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;


    /**
     * 생성
     * */
    @PostMapping("/members/image")
    public ResponseEntity<UploadFile> createProfileImage(HttpServletRequest request,
                                                         @ModelAttribute MultipartFile file) throws IOException {
        UploadFile response = memberService.createProfileImage(file);
        return ResponseEntity.ok().body(response);
    }


    /**
     * 조회
     * */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable("memberId") Long memberId) {
        MemberResponse response = memberService.getById(memberId);

        return ResponseEntity.ok().body(response);
    }

//    @GetMapping("/members/{email}")
//    public ResponseEntity<MemberResponse> getMember(@PathVariable("email") String email) {
//        MemberResponse response = memberService.getByEmail(email);
//
//        return ResponseEntity.ok().body(response);
//    }

    @GetMapping(path = "/members/{email}/image", produces = "image/jpeg")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String email) throws MalformedURLException {
        UrlResource response = memberService.getProfileImage(email);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/members")
    public ResponseEntity<MemberResponse> confirmLogin(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(MemberResponse.from(member));
    }


    /**
     * 수정
     * */
    @PatchMapping("/members")
    public ResponseEntity<Void> modifyMember(@RequestBody UpdateMemberRequest updateMemberRequest,
                                             @AuthenticationPrincipal Member member) {
        memberService.modify(updateMemberRequest, member.getId());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/{email}/image")
    public ResponseEntity<UploadFile> modifyMemberProfile(@PathVariable String email, MultipartFile file) throws IOException {
        UploadFile profileImage = memberService.modifyProfileImage(file, email);

        return ResponseEntity.ok().body(profileImage);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> delete(@PathVariable Long memberId) throws IOException {
        memberService.delete(memberId);

        return ResponseEntity.ok().build();
    }
}
