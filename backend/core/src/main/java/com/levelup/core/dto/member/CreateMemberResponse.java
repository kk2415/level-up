package com.levelup.core.dto.member;

import com.levelup.core.domain.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberResponse {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String birthday;
    private String phone;

}
