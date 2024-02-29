package com.levelup.member.domain.service.dto;

import lombok.Getter;

@Getter
public class UpdateMemberDto {

    private String nickname;

    protected UpdateMemberDto() {}

    protected UpdateMemberDto(String nickname) {
        this.nickname = nickname;
    }

    public static UpdateMemberDto of(String password) {
        return new UpdateMemberDto(password);
    }
}
