package com.levelup.api.dto.member;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ModifyPasswordRequest {

    @NotNull
    @NotBlank
    private String password;
}
