package com.levelup.api.controller.v1.dto.response.member;

import com.levelup.api.controller.v1.dto.SkillDto;
import com.levelup.common.util.DateFormat;
import com.levelup.member.domain.constant.Gender;
import com.levelup.member.domain.constant.RoleName;
import com.levelup.member.domain.service.dto.CreateMemberDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<SkillDto.Response> skills;

    protected CreateMemberResponse() {}

    private CreateMemberResponse(
            Long id,
            String email,
            String name,
            String nickname,
            String birthday,
            Gender gender,
            String phone,
            RoleName role,
            List<SkillDto.Response> skills)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
        this.skills = skills;
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
                dto.getRole(),
                dto.getSkills().stream().map(SkillDto.Response::from).collect(Collectors.toUnmodifiableList())
        );
    }
}
