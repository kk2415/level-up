package com.levelup.api.controller.v1.member;

import com.levelup.api.controller.v1.dto.request.member.UpdatePasswordRequest;
import com.levelup.member.domain.service.MemberService;
import com.levelup.api.controller.v1.dto.response.member.MemberResponse;
import com.levelup.api.controller.v1.dto.request.member.UpdateMemberRequest;
import com.levelup.member.domain.service.dto.MemberDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping({"/{memberId}", "/{memberId}/"})
    public ResponseEntity<MemberResponse> get(@PathVariable Long memberId) {
        MemberDto dto = memberService.get(memberId);

        return ResponseEntity.ok().body(MemberResponse.from(dto));
    }


    @PatchMapping({"/{memberId}","/{memberId}/"})
    public ResponseEntity<Void> update(
            @Valid @RequestPart(value = "request") UpdateMemberRequest request,
            @PathVariable Long memberId
    ) throws IOException {
        memberService.update(request.toDto(), memberId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping({"/{email}/password", "/{email}/password/"})
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request,
            @PathVariable String email)
    {
        memberService.updatePassword(request.toDto(), email);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping({"/{memberId}", "/{memberId}/"})
    public ResponseEntity<Void> delete(@PathVariable Long memberId) {
        memberService.delete(memberId);

        return ResponseEntity.ok().build();
    }
}
