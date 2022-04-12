package com.together.community.controller.dto;

import com.together.community.domain.member.Gender;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class MemberDto {

    @NotNull
    private String emailId;

    @NotNull
    private String emailDomain;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private String birthday;

    @NotNull
    private String phone;

}
