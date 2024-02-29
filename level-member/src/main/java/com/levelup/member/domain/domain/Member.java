package com.levelup.member.domain.domain;

import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.constant.Gender;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.constant.RoleName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member implements Serializable {

    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Gender gender;
    private LocalDate birthday;
    private String phone;
    private UploadFile profileImage;
    private RoleName role;
    private List<MemberSkill> memberSkills;

    protected Member() {}

    private Member(
            Long memberId,
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthday,
            String phone,
            UploadFile profileImage,
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
        this.profileImage = profileImage;
        this.role = role;
    }

    public static Member of(
            Long memberId,
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthday,
            String phone,
            UploadFile profileImage,
            List<MemberSkill> memberSkills)
    {
        return new Member(
            memberId,
            email,
            password,
            name,
            nickname,
            gender,
            birthday,
            phone,
            profileImage,
            RoleName.ANONYMOUS,
            memberSkills
        );
    }

    public static Member of(
            Long memberId,
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthday,
            String phone,
            UploadFile profileImage,
            RoleName role)
    {
        return new Member(
                memberId,
                email,
                password,
                name,
                nickname,
                gender,
                birthday,
                phone,
                profileImage,
                role
        );
    }

    public static Member from(MemberEntity member) {
        return new Member(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getNickname(),
                member.getGender(),
                member.getBirthday(),
                member.getPhone(),
                null,
                member.getRoles().stream()
                        .min(Comparator.comparing(r -> r.getRoleName().getPriority()))
                        .map(Role::getRoleName)
                        .orElse(RoleName.ANONYMOUS),
                member.getMemberSkills().stream()
                        .map(MemberSkill::from)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    public static Member from(MemberEntity member, UploadFile profile) {
        return new Member(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getNickname(),
                member.getGender(),
                member.getBirthday(),
                member.getPhone(),
                profile,
                member.getRoles().stream()
                        .min(Comparator.comparing(r -> r.getRoleName().getPriority()))
                        .map(Role::getRoleName)
                        .orElse(RoleName.ANONYMOUS)
        );
    }

    public MemberEntity toEntity() {
        return MemberEntity.of(null, email, password, name, nickname, gender, birthday, phone, new ArrayList<>(), email);
    }

    public List<Long> getSkillIds() {
        return this.memberSkills.stream()
                .map(memberSkill -> memberSkill.getSkill().getId())
                .collect(Collectors.toUnmodifiableList());
    }
}
