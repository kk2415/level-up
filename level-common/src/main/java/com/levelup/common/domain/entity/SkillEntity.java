package com.levelup.common.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "`skill`")
@Entity
public class SkillEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    protected SkillEntity() {}

    public static SkillEntity of(long id, String name) {
        return new SkillEntity(id, name);
    }
}
