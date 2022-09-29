package com.levelup.member;

import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestSupporter {

    protected Member createMember(Long id, String email, String nickname) {
        Member member = Member.builder()
                .id(id)
                .email(email)
                .password("00000000")
                .name(nickname)
                .nickname(nickname)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(1997, 9, 27))
                .phone("010-2354-9960")
                .profileImage(new UploadFile("default.png", "thumbnail/as154-asda"))
                .roles(new ArrayList<>())
                .build();

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }

    protected Member createMember(String email, String nickname) {
        Member member = Member.builder()
                .email(email)
                .password("00000000")
                .name(nickname)
                .nickname(nickname)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(1997, 9, 27))
                .phone("010-2354-9960")
                .profileImage(new UploadFile("default.png", "thumbnail/as154-asda"))
                .roles(new ArrayList<>())
                .build();

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }
}
