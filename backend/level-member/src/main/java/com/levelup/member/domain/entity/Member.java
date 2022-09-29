package com.levelup.member.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import com.levelup.common.util.file.UploadFile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

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

    @Embedded
    private UploadFile profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Role> roles;

    protected Member (){}

    public void addRole(Role role) {
        role.setMember(this);
        this.roles.add(role);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void modifyProfileImage(UploadFile profileImage) {
        this.profileImage = profileImage;
    }

    public void update(String nickname, UploadFile profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
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
