package com.levelup.member.domain.domain;

import com.levelup.common.domain.domain.Skill;
import com.levelup.member.domain.entity.MemberSkillEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberSkill {

    private Long id;
    private Long memberId;
    private Skill skill;

    public static MemberSkill of(Long memberId, Skill skill) {
        return new MemberSkill(null, memberId, skill);
    }

    public static MemberSkill from(Skill skill) {
        return new MemberSkill(null, null, skill);
    }

    public static MemberSkill from(MemberSkillEntity entity) {
        return new MemberSkill(entity.getId(), entity.getMember().getId(), Skill.from(entity.getSkill()));
    }
}
