package com.levelup.member;

import com.levelup.common.domain.entity.SkillEntity;
import com.levelup.member.domain.constant.Gender;
import com.levelup.member.domain.constant.RoleName;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.member.domain.entity.MemberSkillEntity;
import com.levelup.member.domain.entity.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestSupporter {

    protected MemberEntity createMember(String email, String nickname) {
        MemberEntity member = MemberEntity.of(
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

        List<SkillEntity> skills = List.of(SkillEntity.of(1L, "Spring"), SkillEntity.of(2L, "Java"), SkillEntity.of(2L, "PHP"));
        List<MemberSkillEntity> memberSkills = skills.stream().map(skill -> MemberSkillEntity.of(member, skill)).collect(Collectors.toUnmodifiableList());

        member.addMemberSkills(memberSkills);

        return member;
    }
}
