package com.levelup.api.controller.v1.member;

import com.levelup.api.controller.v1.dto.request.member.AccessTokenRequest;
import com.levelup.api.controller.v1.dto.response.member.AccessTokenResponse;
import com.levelup.member.domain.service.AccessTokenService;
import com.levelup.member.domain.service.dto.AccessTokenDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "액세스 토큰 API")
@RequestMapping("/api/v1/access-token")
@RequiredArgsConstructor
@RestController
public class AccessTokenController {

    private final AccessTokenService accessTokenService;

    @PostMapping({"", "/"})
    public ResponseEntity<AccessTokenResponse> get(@Valid @RequestBody AccessTokenRequest accessTokenRequest) {
        AccessTokenDto dto = accessTokenService.create(accessTokenRequest.toDto());

        return ResponseEntity.ok().body(AccessTokenResponse.from(dto));
    }
}
