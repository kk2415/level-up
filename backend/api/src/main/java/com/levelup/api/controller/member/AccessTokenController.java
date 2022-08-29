package com.levelup.api.controller.member;

import com.levelup.api.dto.member.AccessTokenRequest;
import com.levelup.api.service.AccessTokenService;
import com.levelup.api.util.jwt.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class AccessTokenController {

    private final AccessTokenService accessTokenService;

    @PostMapping("/v1/access-token")
    public ResponseEntity<AccessToken> getAccessToken(@RequestBody AccessTokenRequest accessTokenRequest) {
        AccessToken accessToken = accessTokenService.getAccessToken(accessTokenRequest);

        return ResponseEntity.ok().body(accessToken);
    }
}
