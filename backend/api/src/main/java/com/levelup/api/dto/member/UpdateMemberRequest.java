package com.levelup.api.dto.member;

import com.levelup.core.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateMemberRequest {

    @NotNull
    private String nickname;

    @NotNull
    private UploadFile profileImage;

}