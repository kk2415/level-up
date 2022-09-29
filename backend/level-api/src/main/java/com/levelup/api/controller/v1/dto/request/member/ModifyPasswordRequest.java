package com.levelup.api.controller.v1.dto.request.member;

import com.levelup.member.domain.service.dto.UpdateMemberDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ModifyPasswordRequest {

    @NotNull
    @NotBlank
    private String password;

    protected ModifyPasswordRequest() {}

    private ModifyPasswordRequest(String password) {
        this.password = password;
    }

    public static ModifyPasswordRequest from(String password) {
        return new ModifyPasswordRequest(password);
    }

    public UpdateMemberDto toDto() {
        return UpdateMemberDto.of(password, null, null);
    }
}
