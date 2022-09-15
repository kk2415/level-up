package com.levelup.api.service.dto.member;

import com.levelup.core.domain.file.UploadFile;
import lombok.Getter;

@Getter
public class UpdateMemberDto {

    private String password;
    private String nickname;
    private UploadFile profileImage;

    protected UpdateMemberDto() {}

    private UpdateMemberDto(String password, String nickname, UploadFile profileImage) {
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static UpdateMemberDto of(String password, String nickname, UploadFile profileImage) {
        return new UpdateMemberDto(password, nickname, profileImage);
    }
}
