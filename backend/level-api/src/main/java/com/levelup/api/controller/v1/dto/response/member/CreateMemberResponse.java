package com.levelup.api.controller.v1.dto.response.member;

import com.levelup.common.util.DateFormat;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.service.dto.CreateMemberDto;
import com.levelup.member.domain.service.dto.MemberDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CreateMemberResponse {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String birthday;
    private Gender gender;
    private String phone;
    private RoleName role;

    protected CreateMemberResponse() {}

    private CreateMemberResponse(
            Long id,
            String email,
            String name,
            String nickname,
            String birthday,
            Gender gender,
            String phone,
            RoleName role)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
    }

    public static CreateMemberResponse from(CreateMemberDto dto) {
        return new CreateMemberResponse(
                dto.getMemberId(),
                dto.getEmail(),
                dto.getName(),
                dto.getNickname(),
                dto.getBirthday().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                dto.getGender(),
                dto.getPhone(),
                dto.getRole()
        );
    }
}
