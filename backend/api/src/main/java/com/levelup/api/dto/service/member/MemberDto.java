package com.levelup.api.dto.service.member;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class MemberDto {

    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Gender gender;
    private LocalDate birthday;
    private String phone;
    private UploadFile profileImage;
    private List<RoleName> roles;

    protected MemberDto() {}

    private MemberDto(Long memberId,
                      String email,
                      String password,
                      String name,
                      String nickname,
                      Gender gender,
                      LocalDate birthday,
                      String phone,
                      UploadFile profileImage,
                      List<RoleName> roles) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.profileImage = profileImage;
        this.roles = roles;
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
                member.getProfileImage(),
                member.getRoles().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .birthday(birthday)
                .phone(phone)
                .profileImage(profileImage)
                .articles(new ArrayList<>())
                .comments(new ArrayList<>())
                .channelMembers(new ArrayList<>())
                .roles(new ArrayList<>())
                .build();
    }
}
