package com.levelup.api.dto.member;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
public class MemberResponse implements Serializable {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private Gender gender;
    private LocalDate birthday;
    private String phone;
    private UploadFile uploadFile;
    private List<RoleName> roles;

    public MemberResponse(Long id,
                          String email,
                          String name,
                          String nickname,
                          Gender gender,
                          LocalDate birthday,
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

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
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
}
