package com.levelup.member;

import com.levelup.common.util.file.UploadFile;
import com.levelup.member.domain.entity.Gender;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.Role;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestSupporter {

    protected Member createMember(String email, String nickname) {
        Member member = Member.of(
                null,
                email,
                "00000000",
                nickname,
                nickname,
                Gender.MALE,
                LocalDate.of(1997, 9, 27),
                "010-2354-9960",
                new ArrayList<>(),
                email);

        Role role = Role.of(RoleName.ANONYMOUS, member);
        member.addRole(role);

        return member;
    }
}
