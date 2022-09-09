package com.levelup.api.controller.member;

import com.levelup.api.dto.member.ModifyPasswordRequest;
import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.api.dto.member.MemberResponse;
import com.levelup.api.dto.member.ModifyMemberRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/members/profile")
    public ResponseEntity<UploadFile> createMemberProfileImage(MultipartFile file) throws IOException {
        UploadFile response = memberService.createMemberProfileImage(file);
        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) {
        MemberResponse response = memberService.getById(memberId);

        return ResponseEntity.ok().body(response);
    }



    @PatchMapping("/members/{memberId}")
    public ResponseEntity<Void> modifyMember(@RequestBody ModifyMemberRequest request,
                                             @PathVariable Long memberId) {
        memberService.modify(request, memberId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/{email}/password")
    public ResponseEntity<Void> modifyMemberPassword(@Valid @RequestBody ModifyPasswordRequest request,
                                                     @PathVariable String email) {
        memberService.modifyPassword(request, email);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/{memberId}/profile")
    public ResponseEntity<UploadFile> modifyMemberProfileImage(MultipartFile file, @PathVariable Long memberId) throws IOException {
        UploadFile profileImage = memberService.modifyProfileImage(file, memberId);

        return ResponseEntity.ok().body(profileImage);
    }



    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) throws IOException {
        memberService.delete(memberId);

        return ResponseEntity.ok().build();
    }
}
