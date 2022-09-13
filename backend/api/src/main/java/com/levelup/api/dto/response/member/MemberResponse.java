package com.levelup.api.dto.response.member;

import com.levelup.api.dto.service.member.MemberDto;
import com.levelup.core.DateFormat;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import lombok.Getter;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberResponse implements Serializable {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String birthday;
    private Gender gender;
    private String phone;
    private UploadFile uploadFile;
    private List<RoleName> roles;

    protected MemberResponse() {}

    public MemberResponse(Long id,
                          String email,
                          String name,
                          String nickname,
                          Gender gender,
                          String birthday,
                          String phone,
                          UploadFile uploadFile,
                          List<RoleName> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.uploadFile = uploadFile;
        this.roles = roles;
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
                dto.getProfileImage(),
                dto.getRoles()
        );
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getGender(),
                member.getBirthday().format(DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT)),
                member.getPhone(),
                member.getProfileImage(),
                member.getRoles().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toUnmodifiableList())
                );
    }
}
