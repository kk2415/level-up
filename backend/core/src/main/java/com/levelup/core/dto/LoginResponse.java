package com.levelup.core.dto;

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
}
