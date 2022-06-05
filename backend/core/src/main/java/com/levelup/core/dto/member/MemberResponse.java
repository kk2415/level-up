package com.levelup.core.dto.member;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberResponse {

    private String email;
    private String name;
    private Gender gender;
    private String birthday;
    private String phone;
    private UploadFile uploadFile;

}
