package com.levelup.core.domain.role;

import com.levelup.core.domain.base.BaseTimeEntity;
import com.levelup.core.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Table(name = "role")
@Entity
public class Role extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Role() {}

    private Role(RoleName roleName, Member member) {
        this.roleName = roleName;
        this.member = member;
    }

    public static Role of(RoleName roleName, Member member) {
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
