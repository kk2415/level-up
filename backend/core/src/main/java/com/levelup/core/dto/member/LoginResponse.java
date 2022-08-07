package com.levelup.core.dto.member;

import com.levelup.core.domain.role.RoleName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String email;
    private String token;
    private boolean isAdmin;

    private LoginResponse(Long id, String email, String token, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public static LoginResponse of(Long id, String email, String token, boolean isAdmin) {
        return new LoginResponse(id, email, token, isAdmin);
    }
}
