package com.levelup.member.domain.service.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordDto {

    private String password;

    protected UpdatePasswordDto() {}

    protected UpdatePasswordDto(String password) {
        this.password = password;
    }

    public static UpdatePasswordDto of(String password) {
        return new UpdatePasswordDto(password);
    }
}
