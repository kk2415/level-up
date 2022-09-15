package com.levelup.api.controller.v1.member;

import com.levelup.api.controller.v1.dto.request.member.AccessTokenRequest;
import com.levelup.api.service.AccessTokenService;
import com.levelup.api.util.jwt.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "액세스 토큰 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class AccessTokenController {

    private final AccessTokenService accessTokenService;

    @PostMapping("/access-token")
    public ResponseEntity<AccessToken> get(@RequestBody AccessTokenRequest accessTokenRequest) {
        AccessToken accessToken = accessTokenService.getAccessToken(accessTokenRequest);

        return ResponseEntity.ok().body(accessToken);
    }
}
