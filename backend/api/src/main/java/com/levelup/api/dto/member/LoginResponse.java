package com.levelup.api.dto.member;

import com.levelup.api.util.jwt.AccessToken;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String email;
    private AccessToken accessToken;
    private boolean isAdmin;

    private LoginResponse(Long id, String email, AccessToken token, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.accessToken = token;
        this.isAdmin = isAdmin;
    }

    public static LoginResponse of(Long id, String email, AccessToken token, boolean isAdmin) {
        return new LoginResponse(id, email, token, isAdmin);
    }
}
