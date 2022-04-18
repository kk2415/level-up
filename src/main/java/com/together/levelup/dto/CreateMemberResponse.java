package com.together.levelup.dto;

import com.together.levelup.domain.member.Gender;
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
    private Links links;

}
