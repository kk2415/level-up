package com.levelup.core.dto.member;

import com.levelup.core.domain.member.Authority;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String email;
    private String token;
    private Authority authority;

    private LoginResponse(Long id, String email, String token, Authority authority) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.authority = authority;
    }

    public static LoginResponse of(Long id, String email, String token, Authority authority) {
        return new LoginResponse(id, email, token, authority);
    }
}
