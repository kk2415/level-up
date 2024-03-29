package com.levelup.member.domain.entity;

import com.levelup.common.domain.entity.BaseTimeEntity;
import com.levelup.member.domain.constant.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Table(name = "role")
@Entity
public class Role extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    protected Role() {}

    private Role(RoleName roleName, MemberEntity member) {
        this.roleName = roleName;
        this.member = member;
    }

    public static Role of(RoleName roleName, MemberEntity member) {
        return new Role(roleName, member);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Role)) return false;
        return id != null && id.equals(((Role) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
