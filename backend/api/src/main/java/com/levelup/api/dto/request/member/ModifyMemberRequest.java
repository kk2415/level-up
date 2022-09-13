package com.levelup.api.dto.request.member;

import com.levelup.api.dto.service.member.UpdateMemberDto;
import com.levelup.core.domain.file.UploadFile;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
public class ModifyMemberRequest {

    @NotNull
    private String nickname;

    @NotNull
    private UploadFile profileImage;

    protected ModifyMemberRequest() {}

    private ModifyMemberRequest(String nickname, UploadFile profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static ModifyMemberRequest of(String nickname, UploadFile profileImage) {
        return new ModifyMemberRequest(nickname, profileImage);
    }

    public UpdateMemberDto toDto() {
        return UpdateMemberDto.of(null, nickname, profileImage);
    }
}