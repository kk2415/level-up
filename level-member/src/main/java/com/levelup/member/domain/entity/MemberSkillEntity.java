package com.levelup.member.domain.entity;

import com.levelup.common.domain.entity.BaseTimeEntity;
import com.levelup.common.domain.entity.SkillEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member_skill")
@Entity
public class MemberSkillEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_skill_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @JoinColumn(name = "skill_id")
    @ManyToOne
    private SkillEntity skill;

    protected MemberSkillEntity() {}

    public static MemberSkillEntity of(MemberEntity member, SkillEntity skill) {
        return new MemberSkillEntity(null, member, skill);
    }

    public Long getSkillId() {
        return this.skill.getId();
    }

    public String getSkillName() {
        return this.skill.getName();
    }
}
