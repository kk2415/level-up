package com.together.levelup.dto.member;

import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.member.Gender;
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
