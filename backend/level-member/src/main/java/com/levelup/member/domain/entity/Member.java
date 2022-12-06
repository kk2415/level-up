package com.levelup.member.domain.entity;

import com.levelup.common.domain.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "member_name", nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Role> roles;

    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    protected Member (){}

    public Member(
            Long id,
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthday,
            String phone,
            List<Role> roles,
            String createdBy)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.roles = roles;
        this.createdBy = createdBy;
    }

    public static Member of(
            Long id,
            String email,
            String password,
            String name,
            String nickname,
            Gender gender,
            LocalDate birthday,
            String phone,
            List<Role> roles,
            String createdBy)
    {
        return new Member(id, email, password, name, nickname, gender, birthday, phone, roles, createdBy);
    }

    public void addRole(Role role) {
        role.setMember(this);
        this.roles.add(role);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Member)) return false;
        return id != null && id.equals(((Member) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
