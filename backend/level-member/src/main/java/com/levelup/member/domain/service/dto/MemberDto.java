package com.levelup.member.domain.service.dto;

import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

@Builder
@Getter
public class MemberDto implements Serializable {

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

    protected MemberDto() {}

    private MemberDto(
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

    public static MemberDto of(
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
        return new MemberDto(
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

    public static MemberDto from(Member member) {
        return new MemberDto(
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
                        .orElse(RoleName.ANONYMOUS)
        );
    }

    public static MemberDto from(Member member, UploadFile profile) {
        return new MemberDto(
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

    public Member toEntity() {
        return Member.of(null, email, password, name, nickname, gender, birthday, phone, new ArrayList<>(), email);
    }

    public Member toEntity(UploadFile profileImage) {
        return Member.of(null, email, password, name, nickname, gender, birthday, phone, new ArrayList<>(), email);
    }
}
