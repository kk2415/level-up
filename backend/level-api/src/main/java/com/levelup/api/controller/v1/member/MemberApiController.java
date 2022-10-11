package com.levelup.api.controller.v1.member;

import com.levelup.api.controller.v1.dto.request.member.ModifyPasswordRequest;
import com.levelup.member.domain.service.MemberService;
import com.levelup.common.util.file.UploadFile;
import com.levelup.api.controller.v1.dto.response.member.MemberResponse;
import com.levelup.api.controller.v1.dto.request.member.UpdateMemberRequest;
import com.levelup.member.domain.service.dto.MemberDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final RedisTemplate<String, String> redisTemplate;
    private final MemberService memberService;

    @GetMapping({"/redis/insert", "/redis/insert/"})
    public ResponseEntity<Void> insertKey(@RequestParam String key, @RequestParam String value) {
//        Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(key, value);
        Boolean result = redisTemplate.opsForZSet().add(key, value, 1);
        System.out.println("result : " + result);
        System.out.println("rank : " + redisTemplate.opsForZSet().rank(key, value));
        return ResponseEntity.ok().build();
    }

    @GetMapping({"/redis/delete", "/redis/delete/"})
    public ResponseEntity<Void> deleteKey(@RequestParam String key) {
        Boolean isSuccess = redisTemplate.delete(key);
        System.out.println("isSuccess : " + isSuccess);

        return ResponseEntity.ok().build();
    }

    @PostMapping({"/profile", "/profile/"})
    public ResponseEntity<UploadFile> createProfileImage(MultipartFile file) throws IOException {
        UploadFile response = memberService.createProfileImage(file);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping({"/{memberId}", "/{memberId}/"})
    public ResponseEntity<MemberResponse> get(@PathVariable Long memberId) {
        MemberDto dto = memberService.get(memberId);

        return ResponseEntity.ok().body(MemberResponse.from(dto));
    }


    @PatchMapping({"/{memberId}","/{memberId}/"})
    public ResponseEntity<Void> update(
            @Valid @RequestBody UpdateMemberRequest request,
            @PathVariable Long memberId)
    {
        memberService.update(request.toDto(), memberId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping({"/{email}/password", "/{email}/password/"})
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody ModifyPasswordRequest request,
            @PathVariable String email)
    {
        memberService.updatePassword(request.toDto(), email);

        return ResponseEntity.ok().build();
    }

    @PatchMapping({"/{memberId}/profile", "/{memberId}/profile/"})
    public ResponseEntity<UploadFile> updateProfileImage(
            MultipartFile file,
            @PathVariable Long memberId) throws IOException
    {
        UploadFile profileImage = memberService.updateProfileImage(file, memberId);

        return ResponseEntity.ok().body(profileImage);
    }


    @DeleteMapping({"/{memberId}", "/{memberId}/"})
    public ResponseEntity<Void> delete(@PathVariable Long memberId) {
        memberService.delete(memberId);

        return ResponseEntity.ok().build();
    }
}
