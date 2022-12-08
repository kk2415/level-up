package com.levelup.api.controller.v1.dto.response.member;

import com.levelup.member.domain.service.dto.MemberDto;
import com.levelup.common.util.DateFormat;
import com.levelup.member.domain.constant.Gender;
import com.levelup.member.domain.constant.RoleName;
import lombok.Getter;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberResponse implements Serializable {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String birthday;
    private Gender gender;
    private String phone;
    private RoleName role;

    protected MemberResponse() {}

    public MemberResponse(
            Long id,
            String email,
            String name,
            String nickname,
            Gender gender,
            String birthday,
            String phone,
            RoleName role)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.role = role;
    }

    public static MemberResponse from(MemberDto dto) {
        return new MemberResponse(
                dto.getMemberId(),
                dto.getEmail(),
                dto.getName(),
                dto.getNickname(),
                dto.getGender(),
                dto.getBirthday().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                dto.getPhone(),
                dto.getRole()
        );
    }
}
