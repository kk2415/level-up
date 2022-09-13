package com.levelup.api.dto.member;

import com.levelup.core.domain.file.UploadFile;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyMemberRequest {

    @NotNull
    private String nickname;

    @NotNull
    private UploadFile profileImage;
}