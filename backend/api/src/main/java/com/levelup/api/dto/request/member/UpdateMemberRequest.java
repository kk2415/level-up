package com.levelup.api.dto.request.member;

import com.levelup.api.dto.service.member.UpdateMemberDto;
import com.levelup.core.domain.file.UploadFile;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateMemberRequest {

    @NotNull
    private String nickname;

    @NotNull
    private UploadFile profileImage;

    protected UpdateMemberRequest() {}

    private UpdateMemberRequest(String nickname, UploadFile profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static UpdateMemberRequest of(String nickname, UploadFile profileImage) {
        return new UpdateMemberRequest(nickname, profileImage);
    }

    public UpdateMemberDto toDto() {
        return UpdateMemberDto.of(null, nickname, profileImage);
    }
}