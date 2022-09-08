package com.levelup.api.dto.member;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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
    private boolean isConfirmed;
    private UploadFile uploadFile;

    public MemberResponse(Long id,
                          String email,
                          String name,
                          String nickname,
                          Gender gender,
                          LocalDate birthday,
                          String phone,
                          boolean isConfirmed,
                          UploadFile uploadFile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.isConfirmed = isConfirmed;
        this.uploadFile = uploadFile;
    }

    public static MemberResponse from(Member member, boolean isConfirmed) {
        return new MemberResponse(
                member.getId(),member.getEmail(), member.getName(), member.getNickname(),
                member.getGender(), member.getBirthday(), member.getPhone(), isConfirmed, member.getProfileImage()
        );
    }
}
