package com.levelup.api.dto.member;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.birthday = member.getBirthday();
        this.phone = member.getPhone();
        this.isConfirmed = member.getEmailAuth().getIsConfirmed();
        this.uploadFile = member.getProfileImage();
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member);
    }
}
