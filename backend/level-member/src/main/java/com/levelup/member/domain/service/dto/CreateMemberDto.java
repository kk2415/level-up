package com.levelup.member.domain.service.dto;

import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;

@Getter
public class CreateMemberDto {

    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Gender gender;
    private LocalDate birthday;
    private String phone;
    private RoleName role;

    protected CreateMemberDto() {}

    private CreateMemberDto(
            Long memberId,
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthday,
            String phone,
            RoleName role)
    {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.role = role;
    }

    public static CreateMemberDto from(Member member) {
        return new CreateMemberDto(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getNickname(),
                member.getGender(),
                member.getBirthday(),
                member.getPhone(),
                member.getRoles().stream()
                        .min(Comparator.comparing(r -> r.getRoleName().getPriority()))
                        .map(Role::getRoleName)
                        .orElse(RoleName.ANONYMOUS)
        );
    }
}
