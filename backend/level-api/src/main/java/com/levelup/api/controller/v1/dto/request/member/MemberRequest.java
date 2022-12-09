package com.levelup.api.controller.v1.dto.request.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.levelup.common.domain.domain.Skill;
import com.levelup.member.domain.domain.MemberSkill;
import com.levelup.member.domain.domain.Member;
import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.constant.Gender;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberRequest {

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

    @NotNull
    private String phone;

    List<Long> skillIds = new ArrayList<>();

    private UploadFile uploadFile;

    protected MemberRequest() {}

    private MemberRequest(String email,
                          String password,
                          String name,
                          String nickname,
                          Gender gender,
                          LocalDate birthday,
                          String phone,
                          UploadFile uploadFile) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.uploadFile = uploadFile;
    }

    static public MemberRequest of(
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthday,
            String phone,
            UploadFile uploadFile
    ) {
        return new MemberRequest(email, password, name, nickname, gender, birthday, phone, uploadFile);
    }

    static public MemberRequest from(Member dto) {
        return new MemberRequest(
                dto.getEmail(),
                dto.getPassword(),
                dto.getName(),
                dto.getNickname(),
                dto.getGender(),
                dto.getBirthday(),
                dto.getPhone(),
                dto.getProfileImage());
    }

    public Member toDto() {
        return Member.of(
                null,
                email,
                password,
                name,
                nickname,
                gender,
                birthday,
                phone,
                uploadFile,
                skillIds.stream()
                        .map(id -> MemberSkill.from(Skill.from(id)))
                        .collect(Collectors.toUnmodifiableList())
        );
    }
}