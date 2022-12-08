package com.levelup.common.domain.domain;

import com.levelup.common.domain.entity.SkillEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Skill {

    private Long id;
    private String name;

    public static Skill of(Long id, String name) {
        return new Skill(id, name);
    }

    public static Skill from(Long id) {
        return new Skill(id, "");
    }

    public static Skill from(SkillEntity skillEntity) {
        return new Skill(skillEntity.getId(), skillEntity.getName());
    }
}
