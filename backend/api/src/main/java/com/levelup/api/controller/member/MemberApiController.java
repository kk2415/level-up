package com.levelup.api.controller.member;

import com.levelup.api.dto.request.member.ModifyPasswordRequest;
import com.levelup.api.service.MemberService;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.api.dto.response.member.MemberResponse;
import com.levelup.api.dto.request.member.ModifyMemberRequest;
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
    public ResponseEntity<UploadFile> createProfileImage(MultipartFile file) throws IOException {
        UploadFile response = memberService.createProfileImage(file);
        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> get(@PathVariable Long memberId) {
        MemberResponse response = memberService.getById(memberId);

        return ResponseEntity.ok().body(response);
    }



    @PatchMapping("/members/{memberId}")
    public ResponseEntity<Void> update(@RequestBody ModifyMemberRequest request,
                                             @PathVariable Long memberId) {
        memberService.update(request.toDto(), memberId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/{email}/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody ModifyPasswordRequest request,
                                                     @PathVariable String email) {
        memberService.updatePassword(request.toDto(), email);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/members/{memberId}/profile")
    public ResponseEntity<UploadFile> updateProfileImage(MultipartFile file, @PathVariable Long memberId) throws IOException {
        UploadFile profileImage = memberService.updateProfileImage(file, memberId);

        return ResponseEntity.ok().body(profileImage);
    }



    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> delete(@PathVariable Long memberId) throws IOException {
        memberService.delete(memberId);

        return ResponseEntity.ok().build();
    }
}
