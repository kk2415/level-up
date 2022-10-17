package com.levelup.api.controller.v1.dto.request.member;

import com.levelup.member.domain.service.dto.UpdateMemberDto;
import com.levelup.common.util.file.UploadFile;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateMemberRequest {

    @NotNull
    private String nickname;

    protected UpdateMemberRequest() {}

    private UpdateMemberRequest(String nickname) {
        this.nickname = nickname;
    }

    public static UpdateMemberRequest of(String nickname) {
        return new UpdateMemberRequest(nickname);
    }

    public UpdateMemberDto toDto() {
        return UpdateMemberDto.of(nickname);
    }
}