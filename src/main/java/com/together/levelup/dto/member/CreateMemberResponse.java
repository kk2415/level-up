package com.together.levelup.dto.member;

import com.together.levelup.domain.member.Gender;
import com.together.levelup.dto.Links;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberResponse {

    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String birthday;
    private String phone;

}
