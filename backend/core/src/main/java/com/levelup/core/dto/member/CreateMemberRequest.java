package com.levelup.core.dto.member;

import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.domain.member.Authority;
import com.levelup.core.domain.member.Gender;
import com.levelup.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMemberRequest {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private String birthday;

    @NotNull
    private String phone;

    @NotNull
    private UploadFile uploadFile;

    static public CreateMemberRequest of(
            String email, String password, String name, String nickname,
            Gender gender, String birthday, String phone, UploadFile uploadFile
    ) {
        return new CreateMemberRequest(email, password, name, nickname, gender, birthday, phone, uploadFile);
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
                .authority(Authority.ANONYMOUS)
                .profileImage(uploadFile)
                .channelMembers(new ArrayList<>())
                .articles(new ArrayList<>())
                .build();
    }
}