package com.levelup.api.controller.v1.dto.request.member;

import com.levelup.member.domain.service.dto.UpdateMemberDto;
import com.levelup.member.domain.service.dto.UpdatePasswordDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class UpdatePasswordRequest {

    @NotNull
    @NotBlank
    private String password;

    protected UpdatePasswordRequest() {}

    private UpdatePasswordRequest(String password) {
        this.password = password;
    }

    public static UpdatePasswordRequest from(String password) {
        return new UpdatePasswordRequest(password);
    }

    public UpdatePasswordDto toDto() {
        return UpdatePasswordDto.of(password);
    }
}
