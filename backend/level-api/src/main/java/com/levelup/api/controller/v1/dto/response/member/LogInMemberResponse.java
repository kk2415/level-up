package com.levelup.api.controller.v1.dto.response.member;

import com.levelup.member.domain.service.dto.AccessToken;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LogInMemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private AccessToken accessToken;
    private boolean isAdmin;

    protected LogInMemberResponse() {}

    private LogInMemberResponse(Long id, String email, String nickname, AccessToken token, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.accessToken = token;
        this.isAdmin = isAdmin;
    }

    public static LogInMemberResponse of(Long id, String email, String nickname, AccessToken token, boolean isAdmin) {
        return new LogInMemberResponse(id, email, nickname, token, isAdmin);
    }
}
